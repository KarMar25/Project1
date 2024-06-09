package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.time.LocalDate;


public class VehicleSceneController {

    public static Vehicle selectedVehicle; // wybrany pojazd z poprzedniego okna

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private ListView<String> propertiesList;

    @FXML
    private ImageView vehicleImage;

    @FXML
    void reservePressed(ActionEvent event) {
        for(int i = 0; i < selectedVehicle.getRentalDates().size(); i++){
            if(datePickerFrom.getValue().isAfter(selectedVehicle.getRentalDates().get(i)) || datePickerTo.getValue().isBefore(selectedVehicle.getRentalDates().get(i))){
                // show error message
                return;
            }
        }
        // also trzeba sprawdzac ktory dzisiaj zeby nie mozna bylo rezerwowac pojazdu dzisiaj i w przeszlosci
        for(int i = datePickerFrom.getValue().getDayOfYear(); i < datePickerTo.getValue().getDayOfYear(); i++){
            selectedVehicle.getRentalDates().add(LocalDate.ofYearDay(datePickerFrom.getValue().getYear(), i)); // add all dates between from and to
        }  // nwm czy dziala
    }
    @FXML
    void backPressed(ActionEvent event) { // cofnij do poprzedniego okna
        // do zrobienia jak ogarniemy zmiane scen


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

