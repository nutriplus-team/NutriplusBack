package com.nutriplus.NutriPlusBack.Domain.Patient;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Patient extends PatientModel {

    //Constructor
    public Patient(){
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
    //Set Functions
    public void set_cpf(String cpf_value){
        cpf = cpf_value;
    }
    public void set_name(String name_value){
        name = name_value;
    }
    public void set_date_of_birth(String date_of_birth_value){
        date_of_birth = date_of_birth_value;
    }
    public void set_biological_sex(short biological_sex_value){
        biological_sex = biological_sex_value;
    }
    public void set_ethnic_group(float ethnic_group_value){
        ethnic_group = ethnic_group_value;
    }
    public void set_nutritionist(String nutritionist_value){
        nutritionist = nutritionist_value;
    }
    public void set_food_restrictions(ArrayList<String> food_restrictions_value){
        for(String food : food_restrictions_value){
            food_restrictions.add(food);
        }
    }
    public void set_is_athlete(boolean value){is_athlete = value;}
    public void set_age(int age_value){age = age_value;}
    public void set_physical_activity_level(float physical_activity_level_value){physical_activity_level = physical_activity_level_value;}
    public void set_corporal_mass(float corporal_mass_value){corporal_mass = corporal_mass_value;}
    public void set_height(float height_value){height = height_value;}
    public void set_observations(String observations_value){observations = observations_value;}

    //Fat Folds
    public void set_subscapular(float subscapular_value){subscapular = subscapular_value;}
    public void set_triceps(float triceps_value){triceps = triceps_value;}
    public void set_biceps(float biceps_value){biceps = biceps_value;}
    public void set_chest(float chest_value){chest = chest_value;}
    public void set_axillary(float axillary_value){axillary = axillary_value;}
    public void set_supriailiac(float supriailiac_value){supriailiac = supriailiac_value;}
    public void set_abdominal(float abdominal_value){abdominal = abdominal_value;}
    public void set_thigh(float thigh_value){thigh = thigh_value;}
    public void set_calf(float calf_value){calf = calf_value;}

    //Circumferences
    public void set_wait_circ(float waist_circ_value){waist_circ=waist_circ_value;}
    public void set_abdominal_circ(float abdominal_circ_value){abdominal_circ=abdominal_circ_value;}
    public void set_hips_circ(float hips_circ_value){hips_circ = hips_circ_value;}
    public void set_right_arm_circ(float right_arm_circ_value){right_arm_circ = right_arm_circ_value;}
    public void set_thigh_circ(float thigh_circ_value){thigh_circ = thigh_circ_value;}
    public void set_calf_circ(float calf_circ_value){calf_circ = calf_circ_value;}

    public void set_muscular_mass(float muscular_mass_value){muscular_mass = muscular_mass_value;}
    public void set_corporal_density(float corporal_density_value){corporal_density = corporal_density_value;}
    public void set_body_fat(float body_fat_value){body_fat = body_fat_value;}
    public void set_methabolic_rate(float methabolic_rate_value){methabolic_rate = methabolic_rate_value;}
    public void set_energy_requirements(float energy_requirements_value){energy_requirements = energy_requirements_value;}

    //Get Functions

    public Long get_id() { return id; }
    public String get_cpf(){
        return cpf;
    }
    public String get_name(){
        return name;
    }
    public String get_date_of_birth(){
        return date_of_birth;
    }
    public short get_biological_sex(){
        return biological_sex;
    }
    public float get_ethnic_group(){
        return ethnic_group;
    }
    public String get_nutritionist(){
        return nutritionist;
    }
    public ArrayList<String> get_food_restrictions(){return food_restrictions;}
    public boolean get_is_athlete(){return is_athlete;}
    public int get_age(){return age;}
    public float get_physical_activity_level(){return physical_activity_level;}
    public float get_corporal_mass(){return corporal_mass;}
    public float get_height(){return height;}
    public String get_observations(){return observations;}

    //Fat Folds
    public float get_subscapular(){return subscapular;}
    public float get_triceps(){return triceps;}
    public float get_biceps(){return biceps;}
    public float get_chest(){return chest;}
    public float get_axillary(){return axillary;}
    public float get_supriailiac(){return supriailiac;}
    public float get_abdominal(){return abdominal;}
    public float get_thigh(){return thigh;}
    public float get_calf(){return calf;}

    //Circumferences
    public float get_wait_circ(){return waist_circ;}
    public float get_abdominal_circ(){return abdominal_circ;}
    public float get_hips_circ(){return hips_circ;}
    public float get_right_arm_circ(){return right_arm_circ;}
    public float get_thigh_circ(){return thigh_circ;}
    public float get_calf_circ(){return calf_circ;}

    public float get_muscular_mass(){return muscular_mass;}
    public float get_corporal_density(){return corporal_density;}
    public float get_body_fat(){return body_fat;}
    public float get_methabolic_rate(){return methabolic_rate;}
    public float get_energy_requirements(){return energy_requirements;}

    //Calculate Functions
    public void calculate_energy_requirements(){
        set_energy_requirements(get_methabolic_rate()*get_physical_activity_level());
    }

    public void calculate_methabolic_rate(Constants method){
        //the default is "" string when patient is no athlete.
        float value = 0;
        if(get_is_athlete()){
            switch (method){
                case TINSLEY:
                    value = (float) 24.8 * get_corporal_mass() + 10;
                    break;
                case TINSLEY_NO_FAT:
                    value = (float)(25.9*get_corporal_mass()*(100 - get_body_fat())/100 + 284);
                    break;
                case CUNNINGHAM:
                    value = (float)(22*get_corporal_mass()*(100 - get_body_fat())/100 + 500);
                    break;
            }
        }
        else if (get_biological_sex() == 0) //female
            value = (float) (9.99 * get_corporal_mass() + 6.25 * get_height() - 4.92 * get_age() - 161);
        else                                //male
            value = (float) (9.99 * get_corporal_mass() + 6.25 * get_height() - 4.92 * get_age() + 5);

        set_methabolic_rate(value);
    }

    public void calculate_muscular_mass(){
        set_muscular_mass((float)(get_height()*0.0074*Math.pow(get_right_arm_circ() - 3.1416*get_triceps()/10,2)+0.00088*Math.pow(get_thigh_circ()-3.1416*get_thigh()/10,2)+0.00441*Math.pow(get_calf_circ()-3.1416*get_calf()/10,2)+2.4*get_biological_sex()-0.048*get_age()+get_ethnic_group()+7.8));
    }

    public float sum_seven_skin_folds(){
        return get_subscapular()+get_triceps()+get_chest()+get_axillary()+get_supriailiac()+get_abdominal()+get_thigh();
    }

    public float sum_all_skin_folds(){
        return get_subscapular()+get_triceps()+get_chest()+get_axillary()+get_supriailiac()+get_abdominal()+get_thigh()+get_biceps()+get_calf();
    }

    public void calculate_corporal_density(){
        float corporal_density;

        if (get_biological_sex() == 0) //female
            corporal_density = (float) (1.097 - 0.00046971*sum_seven_skin_folds()+0.00000056*Math.pow(sum_seven_skin_folds(),2)- 0.00012828*get_age());

        else
            corporal_density = (float) (1.112 - 0.00043499*sum_seven_skin_folds() + 0.00000055*Math.pow(sum_seven_skin_folds(),2)-0.00028826*sum_seven_skin_folds()*get_age());

        set_corporal_density(corporal_density);
    }

    public void calculate_body_fat(Constants method){

        float body_fat;
        if(method == Constants.FAULKNER)
            body_fat = (float) ((get_subscapular()+get_triceps()+get_abdominal()+get_supriailiac())*0.153+5.18);
        else
            body_fat = (float) ((4.95/(get_corporal_density()) - 4.5)*100);

        set_body_fat(body_fat);

    }

}
