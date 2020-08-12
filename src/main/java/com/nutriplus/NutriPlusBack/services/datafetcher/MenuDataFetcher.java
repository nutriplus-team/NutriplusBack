package com.nutriplus.NutriPlusBack.services.datafetcher;

import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.menu.Menu;
import com.nutriplus.NutriPlusBack.domain.menu.Portion;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.UserCredentials;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.patient.PatientRecord;
import com.nutriplus.NutriPlusBack.repositories.*;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MenuDataFetcher {

    @Autowired
    private ApplicationMenuRepository applicationMenuRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ApplicationFoodRepository applicationFoodRepository;



    public DataFetcher<Menu> getMenu()
    {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            String uuidMenu = dataFetchingEnvironment.getArgument("uuidMenu");
            Optional<Menu> menu = applicationMenuRepository.getMenuWithPortions(uuidMenu);
            if(!menu.isPresent())
                return null;

            if(menu.get().getPatient().getUuid().equals(uuidPatient))
                return menu.get();

            return null;
        };
    }

    public DataFetcher<List<Menu>> getAllMenusForPatient()
    {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            return applicationMenuRepository.getMenuFromPatient(uuidPatient);
        };
    }

    public DataFetcher<List<Menu>> getMenusForMeal()
    {
        return dataFetchingEnvironment -> {
            String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
            int mealTypeInt = dataFetchingEnvironment.getArgument("mealType");
            Optional<MealType> mealType = MealType.valueOf(mealTypeInt);
            return mealType.map(type -> applicationMenuRepository.getMenusForMeal(uuidPatient, type.name())).orElse(null);
        };
    }

    public DataFetcher<String> addMenu()
    {
        return dataFetchingEnvironment -> {
            try {
                String uuidPatient = dataFetchingEnvironment.getArgument("uuidPatient");
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                UserCredentials user = (UserCredentials) authentication.getCredentials();
                Integer mealTypeInt = dataFetchingEnvironment.getArgument("mealType");
                String uuidRecord = dataFetchingEnvironment.getArgument("uuidRecord");
                PatientRecord patientRecord = applicationUserRepository.findSingleRecord(uuidRecord);

                if (!MealType.valueOf(mealTypeInt).isPresent()) {
                    return "ERROR";
                }
                MealType mealType = MealType.valueOf(mealTypeInt).get();
                ArrayList<String> uuidFoods = dataFetchingEnvironment.getArgument("uuidFoods");
                ArrayList<Double> quantities = dataFetchingEnvironment.getArgument("quantities");
                Patient patient = user.getPatientByUuid(uuidPatient);
                if(patient == null)
                    return "Patient not found";
                if(patientRecord == null)
                    return "Record not found";

                // fetch foods:
                ArrayList<Food> foodList = new ArrayList<>();
                for (String uuidFood : uuidFoods)
                {
                    Food food = applicationFoodRepository.findByUuid(uuidFood);
                    foodList.add(food);
                }

                Menu menu = new Menu(mealType, patient, patientRecord, foodList, quantities);
                applicationMenuRepository.save(menu);
                return menu.getUuid();
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        };
    }

    public DataFetcher<Boolean> removeMenu()
    {
        return dataFetchingEnvironment -> {
            try{
                String uuidMenu = dataFetchingEnvironment.getArgument("uuidMenu");
                applicationMenuRepository.deleteByUuid(uuidMenu);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    public DataFetcher<Boolean> editMenu()
    {
        return dataFetchingEnvironment -> {
            try{
                String uuidMenu = dataFetchingEnvironment.getArgument("uuidMenu");
                ArrayList<String> uuidFoods = dataFetchingEnvironment.getArgument("uuidFoods");
                ArrayList<Double> quantities = dataFetchingEnvironment.getArgument("quantities");
                // remove current portions:
                applicationMenuRepository.removeAllPortions(uuidMenu);
                // add new portions:
                for (int i = 0; i < uuidFoods.size(); i++)
                {
                    Portion portion = new Portion();
                    applicationMenuRepository.addPortion(portion.getUuid(), uuidMenu, uuidFoods.get(i), quantities.get(i));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        };
    }

    public DataFetcher<List<Menu>> getMenusForRecord()
    {
        return dataFetchingEnvironment -> {
            String uuidRecord = dataFetchingEnvironment.getArgument("uuidRecord");
               return applicationMenuRepository.getMenusForRecord(uuidRecord);
        };
    }

}
