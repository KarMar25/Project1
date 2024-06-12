package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Camper extends Vehicle {
    public int numberOfBeds;
    public boolean hasBathroom;
    public Camper(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, int numberOfBeds, int pricePerDay, String vin, String mileage, boolean hasBathroom) {
        super(year, make, model, color, "Camper", powertrain, rentalDates, image, pricePerDay, vin, mileage);
        this.numberOfBeds = numberOfBeds;
        this.hasBathroom = hasBathroom;
    }
    public int getNumberOfBeds() {
        return numberOfBeds;
    }
    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public boolean isHasBathroom() {
        return hasBathroom;
    }

    public void setHasBathroom(boolean hasBathroom) {
        this.hasBathroom = hasBathroom;
    }
}
