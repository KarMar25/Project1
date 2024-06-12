package org.example.terrificproject;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.image.Image;
public class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public Motorcycle(String year, String make, String model, String color, String powertrain, ArrayList<LocalDate> rentalDates, String image, boolean hasSidecar) {
        super(year, make, model, color, "Motorcycle", powertrain, rentalDates, image);
        this.hasSidecar = hasSidecar;
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    public void setSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }
}