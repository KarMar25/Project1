package org.example.terrificproject;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;

public class Car extends Vehicle{
    private int doorCount;
    public Car(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, int doorCount) {
        super(year, make, model, color, "Car", powertrain, rentalDates, image);
        this.doorCount = doorCount;
    }
    public int getDoorCount() {
        return doorCount;
    }
    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }
}

