package com.nutriplus.NutriPlusBack.controllers;

import com.nutriplus.NutriPlusBack.Domain.DTOs.DietNumbersDTO;
import com.nutriplus.NutriPlusBack.domain.dtos.*;
import com.nutriplus.NutriPlusBack.domain.dtos.htmlDtos.FoodHtml;
import com.nutriplus.NutriPlusBack.domain.dtos.htmlDtos.MealOptionHtml;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.food.FoodDTO;
import com.nutriplus.NutriPlusBack.domain.food.NutritionFacts;
import com.nutriplus.NutriPlusBack.domain.food.NutritionFactsDTO;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.menu.GeneratedMenuDTO;
import com.nutriplus.NutriPlusBack.domain.menu.ReplaceDietDTO;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.repositories.ApplicationFoodRepository;
import com.nutriplus.NutriPlusBack.services.EmailService;
import com.nutriplus.NutriPlusBack.services.PdfRenderService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/diet")
public class DietController {

    private final ApplicationFoodRepository foodRepository;
    private PdfRenderService pdfRenderService;
    private EmailService emailService;
    private final ModelMapper modelMapper;

    public DietController(ApplicationFoodRepository foodRepository, PdfRenderService pdfRenderService, EmailService emailService, ModelMapper modelMapper)
    {
        this.foodRepository = foodRepository;
        this.pdfRenderService = pdfRenderService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

    private class ChosenFood
    {
        public Food food;
        public Double quantity;

        public ChosenFood(Food food, Double quantity)
        {
            this.food = food;
            this.quantity = quantity;
        }
    }

    private ErrorDTO addToList(List<MealOptionDTO> from, List<MealOptionHtml> to)
    {
        MealOptionHtml option;
        Food food;
        int optionCount = 0;
        if(from != null)
        {
            for (MealOptionDTO item: from) {
                option = new MealOptionHtml();
                option.foods = new LinkedList<>();
                optionCount++;
                for (PortionDTO portion: item.portions ) {
                    food = foodRepository.findByUuid(portion.foodId);
                    if(food == null)
                    {
                        return new ErrorDTO("Food ID not found");
                    }

                    option.foods.add(new FoodHtml(food, optionCount, portion.portion));
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

    @PostMapping("/send-email-PDF/{patientId}/")
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
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ErrorDTO("Error sending email"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SimpleResponseDTO("OK"));
    }

    @PostMapping("/generate/{patientId}/{meal}/")
    public ResponseEntity<?> generateDiet(@PathVariable String patientId, @RequestBody DietNumbersDTO numbers, @PathVariable int meal)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredentials user = (UserCredentials) authentication.getCredentials();

        Patient patient = user.getPatientByUuid(patientId);
        Optional<MealType> mealType = MealType.valueOf(meal);
        if(patient == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Patient not found"));
        }

        if(!mealType.isPresent())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Meal not found"));
        }

        ArrayList<Food> availableFoods = foodRepository.getPatientEatableFoodForMeal(patient.getFoodRestrictionsUUID(), mealType.get());

        double[] weights = {0.7, 10, 1, 1, 1};
        if(numbers.calories == 0)
            weights[0] = 0;
        if(numbers.proteins == 0)
            weights[1] = 0;
        if(numbers.carbohydrates == 0)
            weights[3] = 0;
        if(numbers.fiber == 0)
            weights[4] = 0;

        ArrayList<Food> itemsForMenu = new ArrayList<>();

        double[] target = {weights[0]* ((double) numbers.calories),
                            weights[1] * ((double) numbers.proteins),
                            weights[2] * ((double) numbers.carbohydrates),
                            weights[3] * ((double) numbers.lipids),
                            weights[4] * ((double) numbers.fiber)};

        int len = availableFoods.size();
        Set<Integer> excluding = new HashSet<Integer>();

        Random rand = new Random();

        for(int i=0; i<8; i++)
        {
            int number = rand.nextInt(len);
            if(excluding.contains(number))
            {
                int tries = 0;
                while(tries < 3 && excluding.contains(number))
                {
                    number = rand.nextInt(len);
                    tries++;
                }
            }
            else
            {
                excluding.add(number);
            }
            itemsForMenu.add(availableFoods.get(number));
        }

        ArrayList<Food> solution = new ArrayList<>();
        ArrayList<Double> quantities = new ArrayList<>();

        meetInTheMiddle(weights, itemsForMenu, solution, quantities, target);

        GeneratedMenuDTO generatedMenuDTO = new GeneratedMenuDTO();

        generatedMenuDTO.quantities = quantities;
        generatedMenuDTO.suggestions = solution.stream().map(this::convertFoodToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(generatedMenuDTO);
    }

    private NutritionFactsDTO convertNutritionFactsToDto(NutritionFacts data)
    {
        return modelMapper.map(data, NutritionFactsDTO.class);
    }

    private FoodDTO convertFoodToDto(Food data)
    {
        FoodDTO food = modelMapper.map(data, FoodDTO.class);
        food.setNutritionFacts(convertNutritionFactsToDto(data.getNutritionFacts()));
        return food;
    }

    private double rmse(double[] predictions, double[] targets)
    {
        double[] subtraction = new double[predictions.length];
        Arrays.parallelSetAll(subtraction, i -> predictions[i]-targets[i]);
        Arrays.parallelSetAll(subtraction, i -> subtraction[i]*subtraction[i]);
        double mean = 0;
        for (double elem : subtraction) {
            mean += elem;
        }
        mean = mean / ((double) subtraction.length);


        return Math.sqrt(mean);
    }

    private void meetInTheMiddle(double[] weights, ArrayList<Food> items, ArrayList<Food> solution, ArrayList<Double> quantities, double[] target)
    {
        double[][] group1 = new double[16][5];
        int test;

        int power;
        for(int i=0; i<16; i++)
        {
            Arrays.parallelSetAll(group1[i], p -> 0);
            for(int j=0; j<4; j++)
            {
                power = (int)Math.pow(2, j);
                test = power & i;
                if(test == power)
                {
                    group1[i][0] = weights[0] * items.get(j).getNutritionFacts().getCalories();
                    group1[i][1] = weights[1] * items.get(j).getNutritionFacts().getProteins();
                    group1[i][2] = weights[2] * items.get(j).getNutritionFacts().getCarbohydrates();
                    group1[i][3] = weights[3] * items.get(j).getNutritionFacts().getLipids();
                    group1[i][4] = weights[4] * items.get(j).getNutritionFacts().getFiber();
                }
            }
        }

        double[][] group2 = new double[16][5];

        for(int i=0; i<16; i++)
        {
            Arrays.parallelSetAll(group2[i], p -> 0);
            for(int j=0; j<4; j++)
            {
                power = (int)Math.pow(2, j);
                test = power & i;
                if(test == power)
                {
                    group2[i][0] = weights[0] * items.get(j+4).getNutritionFacts().getCalories();
                    group2[i][1] = weights[1] * items.get(j+4).getNutritionFacts().getProteins();
                    group2[i][2] = weights[2] * items.get(j+4).getNutritionFacts().getCarbohydrates();
                    group2[i][3] = weights[3] * items.get(j+4).getNutritionFacts().getLipids();
                    group2[i][4] = weights[4] * items.get(j+4).getNutritionFacts().getFiber();
                }
            }
        }

        double[] found = {-1, -1, -1, -1};
        double better = -1;
        boolean begin = true;
        double[] options = {0.5, 1, 1.5, 2};
        double result;

        double[] trial = new double[5];
        int finalOp;
        int finalGr;
        for(int i=0; i<16; i++)
        {
            for(int j=0; j<16; j++)
            {
                for(int k=0; k<4; k++)
                {
                    for(int l=0; l<4; l++)
                    {
                        int finalK = k;
                        int finalI = i;
                        int finalL = l;
                        int finalJ = j;
                        Arrays.parallelSetAll(trial, p-> options[finalL]*group2[finalJ][p] + options[finalK]*group1[finalI][p]);
                        result = rmse(trial, target);
                        if(begin)
                        {
                            better = result;
                            found[0] = i;
                            found[1] = j;
                            found[2] = options[k];
                            found[3] = options[l];
                            begin = false;
                        }
                        else if(result < better)
                        {
                            better = result;
                            found[0] = i;
                            found[1] = j;
                            found[2] = options[k];
                            found[3] = options[l];
                        }

                    }
                }
            }
        }

        if(!begin)
        {
            for(int i=0; i<4; i++)
            {
                power = (int)Math.pow(2, i);
                test = power & (int)found[0];
                if(test == power)
                {
                    solution.add(items.get(i));
                    quantities.add(found[2]);
                }

                test = power & (int)found[1];
                if(test == power)
                {
                    solution.add(items.get(i+4));
                    quantities.add(found[3]);
                }
            }
        }

    }

    @PostMapping("/replace/{patientId}/{meal}/")
    public ResponseEntity<?> replaceDiet(@PathVariable String patientId, @PathVariable int meal, @RequestBody ReplaceDietDTO replaceData)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserCredentials user = (UserCredentials) authentication.getCredentials();

        Patient patient = user.getPatientByUuid(patientId);
        Optional<MealType> mealType = MealType.valueOf(meal);
        if(patient == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Patient not found"));
        }

        if(!mealType.isPresent())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Meal not found"));
        }

        ArrayList<Food> solution = new ArrayList<>();
        ArrayList<Double> quantities = new ArrayList<>();

        int size = replaceData.foods.size();
        if(size != replaceData.quantities.size())
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Lists must have the same size."));
        }

        Food toChange;

        ArrayList<Food> availableFoods = foodRepository.getPatientEatableFoodForMeal(patient.getFoodRestrictionsUUID(), mealType.get());

        ChosenFood chosenFood;
        for(int i=0; i< size; i++)
        {
            toChange = foodRepository.findByUuid(replaceData.foods.get(i));
            if(toChange == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Food not found."));
            }
            chosenFood = findSubstitute(toChange, replaceData.quantities.get(i), availableFoods);

            solution.add(chosenFood.food);
            quantities.add(chosenFood.quantity);
        }

        GeneratedMenuDTO generatedMenuDTO = new GeneratedMenuDTO();

        generatedMenuDTO.quantities = quantities;
        generatedMenuDTO.suggestions = solution.stream().map(this::convertFoodToDto).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(generatedMenuDTO);
    }

    private ChosenFood findSubstitute(Food toChange, double quantity, ArrayList<Food> availableFoods)
    {
        Set<String> excluding = new HashSet<>();
        ArrayList<Food> toEvaluate = new ArrayList<>();
        Food result = null;

        excluding.add(toChange.getUuid());
        int num;
        Random random = new Random();
        int similarSize = availableFoods.size();
        Food checking;

        if (similarSize > 0)
        {
            for(int i=0; i<10; i++)
            {
                num = random.nextInt(similarSize);
                checking = availableFoods.get(num);
                if(excluding.contains(checking.getUuid()))
                {
                    num = random.nextInt(similarSize);
                    checking = availableFoods.get(num);
                }
                else
                {
                    excluding.add(checking.getUuid());
                }

                toEvaluate.add(checking);
            }
        }
        else
        {
            toEvaluate = availableFoods;
        }

        double[] foodProperties = {
                toChange.getNutritionFacts().getCalories(),
                toChange.getNutritionFacts().getProteins(),
                toChange.getNutritionFacts().getCarbohydrates(),
                toChange.getNutritionFacts().getLipids(),
                toChange.getNutritionFacts().getFiber()
        };

        double[] weights = {
                0.7, 10, 1, 1, 1
        };

        Arrays.parallelSetAll(foodProperties, i-> quantity*foodProperties[i]*weights[i]);

        double[] options = {0.5, 1, 1.5, 2};

        double minError = Math.pow(10, 5);

        double bestQty = 0;

        double[] elementProperties = new double[5];
        double[] factoredProperties = new double[5];
        double error;

        for(Food element : toEvaluate)
        {
            elementProperties[0] = element.getNutritionFacts().getCalories();
            elementProperties[1] = element.getNutritionFacts().getProteins();
            elementProperties[2] = element.getNutritionFacts().getCarbohydrates();
            elementProperties[3] = element.getNutritionFacts().getLipids();
            elementProperties[4] = element.getNutritionFacts().getFiber();

            for(double factor : options)
            {
                Arrays.parallelSetAll(factoredProperties, i -> factor*weights[i]*elementProperties[i]);
                error = rmse(factoredProperties, foodProperties);
                if(error <= minError)
                {
                    minError = error;
                    result = element;
                    bestQty = factor;
                }
            }
        }

        return new ChosenFood(result, bestQty);
    }

}
