package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

public abstract class PortionModel {
    @Id
    @GeneratedValue
    public Long id;

    @Relationship(type = "FOOD", direction = Relationship.UNDIRECTED)
    Food food;
    float quantity;
}
