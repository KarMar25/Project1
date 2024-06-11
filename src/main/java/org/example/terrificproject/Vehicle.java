package org.example.terrificproject;
import javafx.scene.image.Image;
import java.time.LocalDate;
import java.util.ArrayList;

public class Vehicle { // jeszcze trzeba dodac class Car itd ktore dziedzicza po Vehicle z dodatkowymi polami
    private String year;
    private String make;
    private String model;
    private String color;
    private String type; // Car, Motorcycle, Pickup, Camper
    private String powertrain; // Internal Combustion Engine (ICE), Hybrid, Battery Electric Vehicle (BEV)
    private ArrayList<LocalDate> rentalDates; // Dates the vehicle is rented out
    public Image image;

    public Vehicle(String year, String make, String model, String color, String type, String powertrain, ArrayList<LocalDate> rentalDates, Image image) {
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


    public void setYear(String year) {
        this.year = year;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPowertrain(String powertrain) {
        this.powertrain = powertrain;
    }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " " + color + " " + type + " " + powertrain;

    }
}
