package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Meal.Meal;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

public abstract class MenuModel {
    @Id
    @GeneratedValue
    public Long id;

    @Relationship(type = "MEALTYPE", direction = Relationship.UNDIRECTED)
    Meal mealType;

    @Relationship(type = "HAS_MENU", direction = Relationship.INCOMING)
    Patient patient;

    @Relationship(type = "PORTIONS", direction = Relationship.UNDIRECTED)
    ArrayList<Portion> portions;
}
