package com.nutriplus.NutriPlusBack.domain.patient;

import java.util.ArrayList;
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
    public void setDateOfBirth(String dateOfBirthValue){
        dateOfBirth = dateOfBirthValue;
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
    public String getDateOfBirth(){
        return dateOfBirth;
    }
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
