package com.nutriplus.NutriPlusBack.Domain.Patient;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.ArrayList;

public abstract class PatientModel{

    @Id
    @GeneratedValue
    public Long id;
    String cpf;
    String name;

    String date_of_birth;
    short biological_sex;   //0 equals female and 1 equals male
    float ethnic_group;     //0 for white/hispanic and 1.1 for afroamerican

    ArrayList<String> food_restrictions = new ArrayList<String>();

    String nutritionist;

    boolean is_athlete ;
    int age;
    float physical_activity_level;
    float corporal_mass;
    float height ;
    String observations;

    //Fat Folds
    float subscapular;
    float triceps;
    float biceps;
    float chest;
    float axillary;
    float supriailiac;
    float abdominal;
    float thigh;
    float calf;

    //Circumferences
    float waist_circ;
    float abdominal_circ;
    float hips_circ;
    float right_arm_circ;
    float thigh_circ;
    float calf_circ;

    float muscular_mass;
    float corporal_density;
    float body_fat;
    float methabolic_rate;
    float energy_requirements;

}