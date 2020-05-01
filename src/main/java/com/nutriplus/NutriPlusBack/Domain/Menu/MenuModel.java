package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Meal;
import com.nutriplus.NutriPlusBack.Domain.Patient.Patient;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

public abstract class MenuModel {
    @Id
    @GeneratedValue
    public Long id;

    @Relationship(type = "MEALTYPE", direction = Relationship.UNDIRECTED)
    Meal meal_type;

    @Relationship(type = "PATIENT", direction = Relationship.UNDIRECTED)
    Patient patient;

    @Relationship(type = "PORTIONS", direction = Relationship.UNDIRECTED)
    Set<Portion> portions;
}
