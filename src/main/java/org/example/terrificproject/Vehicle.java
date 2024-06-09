package org.example.terrificproject;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Vehicle { // jeszcze trzeba dodac class Car itd ktore dziedzicza po Vehicle
    private String year;
    private String make;
    private String model;
    private String color;
    private String type; // Car, Motorcycle, Pickup, Camper
    private String powertrain; // Internal Combustion Engine (ICE), Hybrid, Battery Electric Vehicle (BEV)
    private ArrayList<LocalDate> rentalDates; // Dates the vehicle is rented out
    public Image image;

    public Vehicle(String year, String make, String model, String color, String type, String powertrain, ArrayList<LocalDate> rentalDates, Image image){
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.type = type;
        this.powertrain = powertrain;
        this.rentalDates = rentalDates;
        this.image = image;
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

    public ArrayList<LocalDate> getRentalDates() {
        return rentalDates;
    }

    public void setRentalDates(ArrayList<LocalDate> rentalDates) {
        this.rentalDates = rentalDates;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(year).append('\'');
        sb.append(make).append('\'');
        sb.append(model).append('\'');
        sb.append(color).append('\'');
        sb.append(type).append('\'');
        sb.append(powertrain).append('\'');
        return sb.toString();
    }
}
