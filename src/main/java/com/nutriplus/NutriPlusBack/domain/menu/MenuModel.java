package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import com.nutriplus.NutriPlusBack.domain.meal.Meal;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.meal.MealType;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

//    @Relationship(type = "MEALTYPE", direction = Relationship.UNDIRECTED) -> Causando o erro: Field with primary id is null for entity "MealType.name()"; nested exception is org.neo4j.ogm.exception.core.MappingException
    MealType mealType;

    @Relationship(type = "HAS_MENU", direction = Relationship.INCOMING)
    Patient patient;

    @Relationship(type = "PORTION", direction = Relationship.UNDIRECTED)
    List<Portion> portions;

    public MenuModel()
    {
        super();
    }
}
