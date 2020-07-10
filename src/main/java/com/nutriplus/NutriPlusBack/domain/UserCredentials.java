package com.nutriplus.NutriPlusBack.domain;

import com.nutriplus.NutriPlusBack.domain.dtos.UserRegisterDTO;
import com.nutriplus.NutriPlusBack.domain.food.Food;
import com.nutriplus.NutriPlusBack.domain.patient.Patient;
import com.nutriplus.NutriPlusBack.domain.validators.Validator;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NodeEntity
public class UserCredentials extends AbstractEntity{
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    @Relationship(type = "HAS_PATIENT", direction = Relationship.OUTGOING)
    private ArrayList<Patient> patient_list = new ArrayList<Patient>();
    @Relationship(type = "CUSTOM_FOOD", direction = Relationship.OUTGOING)
    private ArrayList<Food> customFoods = new ArrayList<Food>();
    public UserCredentials()
    {
        super();
    }

    public UserCredentials(String username, String email, String password, String firstName, String lastName)
    {
        super();
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = false;
    }

    public Boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(Boolean state)
    {
        this.isActive = state;
    }


    public Long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patient getPatientByUuid(String uuid)
    {
        return patient_list.stream()
                .filter(patient -> patient.getUuid().equals(uuid))
                .findAny()
                .orElse(null);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPatient(Patient patient){this.patient_list.add(patient);}

    public void deletePatient(Patient patient) {this.patient_list.remove(patient);}

    public void addCustomFood(Food food) {this.customFoods.add(food);}

    public void removeCustomFood(Food food) {this.customFoods.remove(food);}

    
    public ArrayList<Patient> getPatientList(){return this.patient_list;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String Validate(UserRegisterDTO userData)
    {
        Boolean check = Validator.CheckIfIsNullOrEmpty(userData.email) ||
                Validator.CheckIfIsNullOrEmpty(userData.firstName) ||
                Validator.CheckIfIsNullOrEmpty(userData.lastName) ||
                Validator.CheckIfIsNullOrEmpty(userData.password1) ||
                Validator.CheckIfIsNullOrEmpty(userData.password2) ||
                Validator.CheckIfIsNullOrEmpty(userData.username);

        if(check)
        {
            return "Fields should not be empty or null.";
        }

        if(!userData.password1.equals(userData.password2))
        {
            return "Passwords not equal";
        }

        if(userData.password1.length() < 8)
        {
            return "Password too short.";
        }

        if(userData.password1.contains("senha") || userData.password1.contains("password") || userData.password1.contains("1234"))
        {
            return "Password too weak";
        }

        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userData.password1);
        if(!matcher.find())
        {
            return "Password has no special characters.";
        }

        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern patter = Pattern.compile(emailRegex);
        matcher = patter.matcher(userData.email);
        if(!matcher.matches())
        {
            return "Invalid email";
        }

        return null;
    }


}
