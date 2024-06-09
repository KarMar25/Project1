package org.example.terrificproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class RentalController {
    private Button serchButton;

    ArrayList<Vehicle> rentalVehicleList = new ArrayList<Vehicle>(); // baza danych pojazdow

    public void setRentalVehicleList(ArrayList<Vehicle> rentalVehicleList) {
        this.rentalVehicleList = rentalVehicleList;
    }
    @FXML
    public void onButtonClick(ActionEvent event) {
            StringBuilder carsText = new StringBuilder();
            for (Vehicle car : rentalVehicleList) {
                carsText.append(car.toString()).append("\n");
            }
        }
    }