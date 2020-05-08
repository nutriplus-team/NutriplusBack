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

    String dateOfBirth;
    short biologicalSex;   //0 equals female and 1 equals male
    float ethnicGroup;     //0 for white/hispanic and 1.1 for afroamerican

    ArrayList<String> foodRestrictions = new ArrayList<String>();

    String nutritionist;

    boolean isAthlete ;
    int age;
    float physicalActivityLevel;
    float corporalMass;
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
    float waistCirc;
    float abdominalCirc;
    float hipsCirc;
    float rightArmCirc;
    float thighCirc;
    float calfCirc;

    float muscularMass;
    float corporalDensity;
    float bodyFat;
    float methabolicRate;
    float energyRequirements;

}