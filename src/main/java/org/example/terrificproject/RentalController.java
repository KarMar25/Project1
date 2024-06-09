package org.example.terrificproject;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.*;
import java.util.stream.Collectors;

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
        Vehicle vehicle1 = new Vehicle("2021", "Toyota", "Corolla", "Black", "Sedan", "ICE", rentalDates);
        Vehicle vehicle2 = new Vehicle("2020", "Miata", "Corolla", "Black", "Sedan", "Hybrid",rentalDates);
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
        vehiclesList.getItems().clear();
        String[] wordList = searchField.getText().split(" ");// podzielenie tekstu na slowa
        System.out.println(wordList); // test
        HashMap<Vehicle, Integer> order = new HashMap<Vehicle, Integer>();
        for (Vehicle vehicle : vehicles) {
            int wordsMatched = 0; //
            for (String word : wordList) {
                if (vehicle.getMake().contains(word)) {
                    wordsMatched++;
                }
                if (vehicle.getModel().contains(word)) {
                    wordsMatched++;
                }
                if (vehicle.getYear().contains(word)) {
                    wordsMatched++;
                }
                if (vehicle.getColor().contains(word)) {
                    wordsMatched++;
                }
                if (vehicle.getType().contains(word)) {
                    wordsMatched++;
                }
                if (vehicle.getPowertrain().contains(word)) {
                    wordsMatched++;
                }
            }

            if (wordsMatched == 0) continue; // if no words match do not add to the list
            order.put(vehicle, wordsMatched);
        }

        List<Map.Entry<Vehicle, Integer>> sortedEntries = order.entrySet().stream()
                .sorted(Map.Entry.<Vehicle, Integer>comparingByValue().reversed())
                .toList(); // sort the vehicles by the number of words matched

        for (Map.Entry<Vehicle, Integer> entry : sortedEntries) {
            vehiclesList.getItems().add(new Text(entry.getKey().toString()));// display vehicles in the correct order
        }
    }



}
