package com.nutriplus.NutriPlusBack.Controllers;

import com.nutriplus.NutriPlusBack.Domain.DTOs.DietDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.ErrorDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.MealOptionDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.PortionDTO;
import com.nutriplus.NutriPlusBack.Domain.DTOs.htmlDtos.FoodHtml;
import com.nutriplus.NutriPlusBack.Domain.DTOs.htmlDtos.MealOptionHtml;
import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/diet")
public class DietController {

    private final ApplicationFoodRepository foodRepository;
    private TemplateEngine nutriplusTemplateEngine;
    private static final String DIET_TEMPLATE = "html/dietTemplate.html";

    public DietController(ApplicationFoodRepository foodRepository, TemplateEngine nutriplusTemplateEngine)
    {
        this.foodRepository = foodRepository;
        this.nutriplusTemplateEngine = nutriplusTemplateEngine;
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
        errorDTO = addToList(diet.breakfast, breakfast);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        errorDTO = addToList(diet.morningSnack, morningSnack);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        errorDTO = addToList(diet.lunch, lunch);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        errorDTO = addToList(diet.afternoonSnack, afternoonSnack);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        errorDTO = addToList(diet.workoutSnack, workoutSnack);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        errorDTO = addToList(diet.dinner, dinner);
        if(errorDTO != null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
        }

        Locale local = new Locale("pt");
        final Context ctx = new Context(local);
        ctx.setVariable("breakfast", breakfast);
        ctx.setVariable("morningSnack", morningSnack);
        ctx.setVariable("lunch", lunch);
        ctx.setVariable("afternoonSnack", afternoonSnack);
        ctx.setVariable("workoutSnack", workoutSnack);
        ctx.setVariable("dinner", dinner);

        final String htmlContent = this.nutriplusTemplateEngine.process(DIET_TEMPLATE, ctx);

        return ResponseEntity.status(HttpStatus.OK).body(new ErrorDTO(htmlContent));

    }
}
