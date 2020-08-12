package com.nutriplus.NutriPlusBack.domain.patient;

import java.util.List;

public class PatientRecordDTO extends PatientRecord {

    private List<String> menus;

    public PatientRecordDTO()
    {

    }

    public void setMenus(List<String> menus) {
        this.menus = menus;
    }

    public List<String> getMenus()
    {
        return this.menus;
    }
}
