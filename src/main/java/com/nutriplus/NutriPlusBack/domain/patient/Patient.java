package com.nutriplus.NutriPlusBack.domain.patient;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Patient extends PatientModel {

    //Constructor
    public Patient(){
        super();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public void removePatientRecord(PatientRecord record){patientRecordList.remove(record);}
    public void updateLastRecord(){lastRecord = getLastPatientRecord();}

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
    public void setBiologicalSex(short biologicalSexValue){
        biologicalSex = biologicalSexValue;
    }
    public void setEthnicGroup(float ethnicGroupValue){
        ethnicGroup = ethnicGroupValue;
    }
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
    public short getBiologicalSex(){
        return biologicalSex;
    }
    public float getEthnicGroup(){
        return ethnicGroup;
    }
    public String getNutritionist(){
        return nutritionist;
    }
    public ArrayList<String> getFoodRestrictions(){return foodRestrictions;}
    public ArrayList<PatientRecord> getPatientRecordList(){return patientRecordList;}
    public PatientRecord getLastPatientRecord(){

        if(!getPatientRecordList().isEmpty())
            return getPatientRecordList().get(getPatientRecordList().size()-1);

        else return null;
    }

}
