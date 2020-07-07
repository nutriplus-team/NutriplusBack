package com.nutriplus.NutriPlusBack.domain.patient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Patient extends PatientModel {

    //Constructor
    public Patient(){
        super();
    }

    //Set Functions
    public void setPatientRecord(PatientRecord recordList){patientRecordList.add(recordList);}
    public void setCpf(String cpfValue){
        cpf = cpfValue;
    }
    public void setName(String nameValue){
        name = nameValue;
    }
    public void setDateOfBirth(String date){
        String[] values = date.split("/");
        int day = Integer.parseInt(values[0]);
        int month = Integer.parseInt(values[1]);
        int year = Integer.parseInt(values[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        dateOfBirth = calendar.getTime();
    }
    public void setBiologicalSex(Short biologicalSexValue){
        biologicalSex = biologicalSexValue;
    }
    public void setEthnicGroup(Double ethnicGroupValue){ ethnicGroup = ethnicGroupValue; }
    public void setNutritionist(String nutritionistValue){
        nutritionist = nutritionistValue;
    }
    public void setFoodRestrictionsUUID(ArrayList<String> foodRestrictionsUUID){
        foodRestrictions.addAll(foodRestrictionsUUID);
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    //Get Functions
    public String getEmail()
    {
        return this.email;
    }
    public Long getId() { return id; }
    public String getCpf(){
        return cpf;
    }
    public String getName(){
        return name;
    }
    public String getDateOfBirth(){ return new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth); }
    public Short getBiologicalSex(){
        return biologicalSex;
    }
    public Double getEthnicGroup(){
        return ethnicGroup;
    }
    public String getNutritionist(){
        return nutritionist;
    }
    public ArrayList<String> getFoodRestrictionsUUID(){return foodRestrictions;}
    public ArrayList<PatientRecord> getPatientRecordList(){return patientRecordList;}
    public PatientRecord getLastPatientRecord(){
        int sizeList = getPatientRecordList().size();
        if(sizeList>0) {
            return getPatientRecordList().get(sizeList-1);
        }

        else return null;
    }

}
