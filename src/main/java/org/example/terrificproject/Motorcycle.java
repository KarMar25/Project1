package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Motorcycle extends Vehicle {
    public boolean hasSidecar;
    public String topSpeed;
    public boolean abs;

    public Motorcycle(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, boolean hasSidecar, int pricePerDay, String vin, String mileage, String topSpeed, boolean abs) {
        super(year, make, model, color, "Motorcycle", powertrain, rentalDates, image, pricePerDay, vin, mileage);
        this.hasSidecar = hasSidecar;
        this.topSpeed = topSpeed;
        this.abs = abs;
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    public void setSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    public String getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(String topSpeed) {
        this.topSpeed = topSpeed;
    }

    public boolean isAbs() {
        return abs;
    }

    public void setAbs(boolean abs) {
        this.abs = abs;
    }
}