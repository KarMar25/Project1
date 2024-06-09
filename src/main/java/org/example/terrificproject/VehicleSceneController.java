package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class VehicleSceneController {

    public static Vehicle selectedVehicle;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<String> propertiesList;

    @FXML
    private ImageView vehicleImage;

    @FXML
    void reservePressed(ActionEvent event) {
        selectedVehicle.getRentalDates().add(java.sql.Date.valueOf(datePicker.getValue()));

    }
    @FXML
    void backPressed(ActionEvent event) {


    }

    @FXML
    void initialize() {
        propertiesList.getItems().add("Year: " + selectedVehicle.getYear());
        propertiesList.getItems().add("Make: " + selectedVehicle.getMake());
        propertiesList.getItems().add("Model: " + selectedVehicle.getModel());
        propertiesList.getItems().add("Color: " + selectedVehicle.getColor());
        propertiesList.getItems().add("Type: " + selectedVehicle.getType());
        propertiesList.getItems().add("Powertrain: " + selectedVehicle.getPowertrain());
        vehicleImage.setImage(selectedVehicle.getImage());

    }

}

