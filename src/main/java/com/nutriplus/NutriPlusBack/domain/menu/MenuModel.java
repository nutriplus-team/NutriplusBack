package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

public abstract class MenuModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

    MealType mealType;

    @Relationship(type = "HAS_MENU", direction = Relationship.INCOMING)
    Patient patient;

    @Relationship(type = "PORTION", direction = Relationship.OUTGOING)
    ArrayList<Portion> portions;

    public MenuModel()
    {
        super();
    }
}
