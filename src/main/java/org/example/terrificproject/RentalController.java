package org.example.terrificproject;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Date;

public class RentalController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Text> vehiclesList;

    public ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>(); // baza danych pojazdow

    @FXML
    public void initialize() {
        addingVehicles(vehicles); // dodanie pojazdow do bazy danych
        for (Vehicle vehicles : vehicles) {
            vehiclesList.getItems().add(new Text(vehicles.toString()));
        }

    }

    private void addingVehicles(ArrayList<Vehicle> vehicles) {
        ArrayList<Date> rentalDates = new ArrayList<Date>(); // dla kazdego pojazdu bedzie inna  ale teraz jest tak
        Vehicle vehicle1 = new Vehicle("2020", "Toyota", "Corolla", "Black", "Sedan", "ICE", rentalDates);
        Vehicle vehicle2 = new Vehicle("2020", "Toyota", "Corolla", "Black", "Sedan", "Hybrid",rentalDates);
        Vehicle vehicle3 = new Vehicle("2020", "Toyota", "Corolla", "Black", "Sedan", "BEV",rentalDates);
        Vehicle vehicle4 = new Vehicle("2020", "Toyota", "Corolla", "Black", "Sedan", "ICE",rentalDates);
        Vehicle vehicle5 = new Vehicle("2020", "Toyota", "Corolla", "Black", "Sedan", "Hybrid",rentalDates);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
    }

    @FXML
    void loginPressed(ActionEvent event) {

    }
    @FXML
    void searchPressed(ActionEvent event) {
        searchForVehicle(vehicles, searchField.getText());

    }

    private void searchForVehicle(ArrayList<Vehicle> vehicles, String text) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPowertrain().contains(text)) { // zaleznie od kategorii
                System.out.println(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getColor() + " " + vehicle.getType() + " " + vehicle.getPowertrain());
            }
        }
    }


}
