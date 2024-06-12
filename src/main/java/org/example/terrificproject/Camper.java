package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Camper extends Vehicle {
    private int numberOfBeds;
    public Camper(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, Image image, int numberOfBeds) {
        super(year, make, model, color, "Camper", powertrain, rentalDates, image);
        this.numberOfBeds = numberOfBeds;
    }
    public int getNumberOfBeds() {
        return numberOfBeds;
    }
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }
}
