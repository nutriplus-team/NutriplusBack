package com.nutriplus.NutriPlusBack.domain;

import java.util.UUID;

public abstract class AbstractEntity {
    protected String uuid;
    public AbstractEntity()
    {
        this.uuid = UUID.randomUUID().toString().replace("-", "");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
