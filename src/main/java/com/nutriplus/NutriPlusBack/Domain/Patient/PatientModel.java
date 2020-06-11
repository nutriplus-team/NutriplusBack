package com.nutriplus.NutriPlusBack.Domain.Patient;

import com.nutriplus.NutriPlusBack.Domain.AbstractEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

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