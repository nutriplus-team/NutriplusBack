package com.nutriplus.NutriPlusBack.Domain.Menu;

import com.nutriplus.NutriPlusBack.Domain.AbstractEntity;
import com.nutriplus.NutriPlusBack.Domain.Food.Food;
import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;

public abstract class PortionModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

    @Property
    float quantity;

    @StartNode Menu menu;
    @EndNode   Food food;

    public PortionModel()
    {
        super();
    }
}
