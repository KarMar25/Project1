package org.example.terrificproject;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
        HashMap<Vehicle, Integer> order = new HashMap<Vehicle, Integer>();
        for(Vehicle vehicle : vehicles){
            int wordsMatched = 0; // ilosc slow ktore pasuja do pojazdu
            for(String word : wordList){
                if(vehicle.getMake().contains(word)){
                    wordsMatched++;
                }
                if(vehicle.getModel().contains(word)){
                    wordsMatched++;
                }
                if(vehicle.getYear().contains(word)){
                    wordsMatched++;
                }
                if(vehicle.getColor().contains(word)){
                    wordsMatched++;
                }
                if(vehicle.getType().contains(word)){
                    wordsMatched++;
                }
                if(vehicle.getPowertrain().contains(word)){
                    wordsMatched++;
                }
            }
            if(wordsMatched == 0) continue; // jesli nie pasuje zadne slowo to nie dodajemy do listy
            order.put(vehicle, wordsMatched);
        }
        order.entrySet().stream().sorted((k1, k2) -> -k2.getValue().compareTo(k1.getValue())); // sortowanie po ilosci slow ktore pasuja
        for (Vehicle key : order.keySet()) { // dodanie pojazdow do listy w odpowiedniej kolejnosci
            vehiclesList.getItems().add(new Text(key.toString())); // wyswietlenie pojazdow w odpowiedniej kolejnosci
        }

    }


}
