package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;


public class Car extends Vehicle {
    public int doorCount;
    public String trunkCapacity;

    public Car(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, int doorCount, int pricePerDay, String vin, String mileage, String trunkCapacity) {
        super(year, make, model, color, "Car", powertrain, rentalDates, image, pricePerDay, vin, mileage);
        this.doorCount = doorCount;
        this.trunkCapacity = trunkCapacity;
    }

    public int getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }

    public String getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(String trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
    }
}

