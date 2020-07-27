package com.nutriplus.NutriPlusBack.domain.patient;

import com.nutriplus.NutriPlusBack.domain.AbstractEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.Date;

public abstract class PatientModel extends AbstractEntity {

    @Id
    @GeneratedValue
    public Long id;

    String name;
    protected String email;
    String cpf;
    String dateOfBirth;
    short biologicalSex;   //0 equals female and 1 equals male
    Double ethnicGroup;     //0 for white/hispanic and 1.1 for afroamerican

    //Array of uuids
    ArrayList<String> foodRestrictions = new ArrayList<>();

    @Relationship(type = "HAS_RECORD", direction = Relationship.OUTGOING)
    ArrayList<PatientRecord> patientRecordList = new ArrayList<PatientRecord>();

    String nutritionist;

    public PatientModel()
    {
        super();
    }
}