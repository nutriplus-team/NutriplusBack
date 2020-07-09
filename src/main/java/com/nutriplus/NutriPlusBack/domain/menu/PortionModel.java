package com.nutriplus.NutriPlusBack.domain.menu;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import org.neo4j.ogm.annotation.*;

public abstract class PortionModel extends AbstractEntity {
    @Id
    @GeneratedValue
    public Long id;

    @Property
    Double quantity;

    @StartNode Menu menu;
    @EndNode   Food food;

    public PortionModel()
    {
        super();
    }
}
