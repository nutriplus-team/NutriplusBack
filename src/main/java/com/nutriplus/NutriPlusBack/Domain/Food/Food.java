package com.nutriplus.NutriPlusBack.Domain.Food;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Food {
    @Id
    @GeneratedValue
    public Long id;
    // PLACEHOLDER: Deve ser substituído pela implementação final
}
