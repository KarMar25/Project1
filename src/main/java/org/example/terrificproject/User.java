package org.example.terrificproject;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;

public class User {
    private String name;
    private String surname;
    private String username;
    private String password;
    private HashMap<Vehicle, ArrayList<LocalDate>> rentedVehicles;

    public User(String name, String surname, String username, String password, HashMap<Vehicle, ArrayList<LocalDate>> rentedVehicles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
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

    public HashMap<Vehicle, ArrayList<LocalDate>> getRentedVehicles() {
        return rentedVehicles;
    }
}