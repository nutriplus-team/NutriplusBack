package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;

public abstract class PortionModel {
    @Id
    @GeneratedValue
    public Long id;

    @Property
    float quantity;

    @StartNode Menu menu;
    @EndNode   Food food;
}
