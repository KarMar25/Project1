package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Tractor extends Vehicle {
    public String trunkCapacity;
    public String cabType;

    public Tractor(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, int pricePerDay, String vin, String mileage, String trunkCapacity, String cabType) {
        super(year, make, model, color, "Tractor", powertrain, rentalDates, image, pricePerDay, vin, mileage);
        this.trunkCapacity = trunkCapacity;
        this.cabType = cabType;
    }

    public String getTrunkCapacity() {
        return trunkCapacity;
    }

    public void setTrunkCapacity(String trunkCapacity) {
        this.trunkCapacity = trunkCapacity;
    }

    public String getCabType() {
        return cabType;
    }

    public void setCabType(String cabType) {
        this.cabType = cabType;
    }
}
