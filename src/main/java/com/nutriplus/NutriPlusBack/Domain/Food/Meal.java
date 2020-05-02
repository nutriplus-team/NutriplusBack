package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Meal {
    @Id
    @GeneratedValue
    public Long id;

    public Long get_id() { return id; }

    // PLACEHOLDER: Deve ser substituído pela implementação final
}
