package org.example.terrificproject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

public class User {
    private String name;
    private String surname;
    private String username;
    private String password;
    private HashMap<String, ArrayList<LocalDate>> rentedVehicles;

    public User(String name, String surname, String username, String password, HashMap<String, ArrayList<LocalDate>> rentedVehicles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.rentedVehicles = rentedVehicles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRentedVehicles(HashMap<String, ArrayList<LocalDate>> rentedVehicles) {
        this.rentedVehicles = rentedVehicles;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, ArrayList<LocalDate>> getRentedVehicles() {
        return rentedVehicles;
    }
}