package com.nutriplus.NutriPlusBack.Controllers;

import com.nutriplus.NutriPlusBack.Domain.DTOs.*;
import com.nutriplus.NutriPlusBack.Domain.DTOs.htmlDtos.FoodHtml;
import com.nutriplus.NutriPlusBack.Domain.DTOs.htmlDtos.MealOptionHtml;
import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import com.nutriplus.NutriPlusBack.Domain.UserCredentials;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationFoodRepository;
import com.nutriplus.NutriPlusBack.Services.EmailService;
import com.nutriplus.NutriPlusBack.Services.PdfRenderService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/diet")
public class DietController {

    private final ApplicationFoodRepository foodRepository;
    private PdfRenderService pdfRenderService;
    private EmailService emailService;

    public DietController(ApplicationFoodRepository foodRepository, PdfRenderService pdfRenderService, EmailService emailService)
    {
        this.foodRepository = foodRepository;
        this.pdfRenderService = pdfRenderService;
        this.emailService = emailService;
    }

    private ErrorDTO addToList(List<MealOptionDTO> from, List<MealOptionHtml> to)
    {
        MealOptionHtml option;
        Optional<Food> food;
        int optionCount = 0;
        if(from != null)
        {
            for (MealOptionDTO item: from) {
                option = new MealOptionHtml();
                option.foods = new LinkedList<>();
                optionCount++;
                for (PortionDTO portion: item.portions ) {
                    food = foodRepository.findById(portion.foodId);
                    if(!food.isPresent())
                    {
                        return new ErrorDTO("Food ID not found");
                    }

                    option.foods.add(new FoodHtml(food.get(), optionCount, portion.portion));
                }
                to.add(option);
            }
        }

        return null;
    }

    private ErrorDTO prepareLists(List<MealOptionHtml> breakfast, List<MealOptionHtml> morningSnack, List<MealOptionHtml> lunch,
        List<MealOptionHtml> afternoonSnack, List<MealOptionHtml> workoutSnack, List<MealOptionHtml> dinner, DietDTO diet)
    {
        ErrorDTO errorDTO;
        errorDTO = addToList(diet.breakfast, breakfast);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        errorDTO = addToList(diet.morningSnack, morningSnack);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        errorDTO = addToList(diet.lunch, lunch);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        errorDTO = addToList(diet.afternoonSnack, afternoonSnack);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        errorDTO = addToList(diet.workoutSnack, workoutSnack);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        errorDTO = addToList(diet.dinner, dinner);
        if(errorDTO != null)
        {
            return errorDTO;
        }

        return null;
    }

    @PostMapping("/generate-PDF/")
    public ResponseEntity<?> generateDietPdf(@RequestBody DietDTO diet)
    {
        List<MealOptionHtml> breakfast = new LinkedList<>();
        List<MealOptionHtml> morningSnack = new LinkedList<>();
        List<MealOptionHtml> lunch = new LinkedList<>();
        List<MealOptionHtml> afternoonSnack = new LinkedList<>();
        List<MealOptionHtml> workoutSnack = new LinkedList<>();
        List<MealOptionHtml> dinner = new LinkedList<>();

        ErrorDTO errorDTO;
        errorDTO = prepareLists(breakfast, morningSnack, lunch, afternoonSnack, workoutSnack, dinner, diet);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        try
        {
            byte[] fileStream = pdfRenderService.renderDiet(breakfast, morningSnack, lunch, afternoonSnack, workoutSnack, dinner);
            String encoded = Base64.encodeBase64String(fileStream);
            FileDTO fileDTO = new FileDTO(encoded);
            return ResponseEntity.status(HttpStatus.OK).body(fileDTO);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorDTO("Error generating PDF"));
        }

    }

    @PostMapping("send-email-PDF/{patientId}/")
    public ResponseEntity<?> sendDietViaEmail(@RequestBody DietDTO diet, @PathVariable String patientId)
    {

        List<MealOptionHtml> breakfast = new LinkedList<>();
        List<MealOptionHtml> morningSnack = new LinkedList<>();
        List<MealOptionHtml> lunch = new LinkedList<>();
        List<MealOptionHtml> afternoonSnack = new LinkedList<>();
        List<MealOptionHtml> workoutSnack = new LinkedList<>();
        List<MealOptionHtml> dinner = new LinkedList<>();

        ErrorDTO errorDTO;
        errorDTO = prepareLists(breakfast, morningSnack, lunch, afternoonSnack, workoutSnack, dinner, diet);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredentials user = (UserCredentials) authentication.getCredentials();

        Patient patient = user.getPatientByUuid(patientId);
        if(patient == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Patient not found"));
        }

        try{
            emailService.sendDietEmail(user.getFirstName(), patient.getEmail(), patient.getName(), breakfast, morningSnack, lunch,
                    afternoonSnack, workoutSnack, dinner, diet);
        }
        catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorDTO("Error sending email"));
        }

        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
