package org.example.terrificproject;

import java.util.ArrayList;
import java.util.Date;

public class Vehicle {
    private String year;
    private String make;
    private String model;
    private String color;
    private String type; // Car, Motorcycle, Pickup, Camper
    private String powertrain; // Internal Combustion Engine (ICE), Hybrid, Battery Electric Vehicle (BEV)
    private ArrayList<Date> rentalDates = new ArrayList<>(); // Dates the vehicle is rented out

    public Vehicle(String year, String make, String model, String color, String type, String powertrain, ArrayList<Date> rentalDates){
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
        this.powertrain = powertrain;
        this.rentalDates = rentalDates;
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public String getPowertrain() {
        return powertrain;
    }

    public ArrayList<Date> getRentalDates() {
        return rentalDates;
    }

    public void setRentalDates(ArrayList<Date> rentalDates) {
        this.rentalDates = rentalDates;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vehicle{");
        sb.append("year='").append(year).append('\'');
        sb.append(", make='").append(make).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", power train='").append(powertrain).append('\'');
        sb.append(", rentalDates=").append(rentalDates);
        sb.append('}');
        return sb.toString();
    }
}
