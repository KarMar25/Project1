package org.example.terrificproject;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import java.io.IOException;

import java.time.LocalDate;
import java.util.*;

public class RentalController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<Text> vehiclesList;

    public ArrayList<Vehicle> vehicles = new ArrayList<>(); // baza danych pojazdow

    @FXML
    public void initialize() throws IOException {
        addingVehicles();
        for (Vehicle vehicles : vehicles) {
            vehiclesList.getItems().add(new Text(vehicles.toString()));
        }

    }

    private void addingVehicles() throws IOException {
        ArrayList<LocalDate> rentalDates = new ArrayList<LocalDate>();
        // dla kazdego vehicle bedzie inny date i obrazek ale nie chcialo mi sie
        // trzeba tez te vehicles zrobic w jsonie, zeby rezerwacja nadpisywala plik
        // dzieki czemu po kazdym otwarciu nie bedzie sie zerowac
        // albo ogolnie zeby rezerwacje byly zapisywane w pliku


        Image image = new Image("file:src/main/resources/org/example/terrificproject/miata.jpg");
        Vehicle vehicle1 = new Vehicle("2021", "Toyota", "Corolla", "Black", "Sedan", "ICE", rentalDates, image);
        Vehicle vehicle2 = new Vehicle("2021", "Mazda", "Miata", "Red", "Convertible", "ICE", rentalDates, image);
        Vehicle vehicle3 = new Vehicle("2021", "Ford", "F-150", "White", "Pickup", "ICE", rentalDates, image);
        Vehicle vehicle4 = new Vehicle("2021", "Harley-Davidson", "Road King", "Black", "Motorcycle", "ICE", rentalDates, image);
        Vehicle vehicle5 = new Vehicle("2021", "Winnebago", "Revel", "White", "Camper", "ICE", rentalDates, image);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
    }

    @FXML
    void loginPressed(ActionEvent event) {
        // jak sie komus bardzo nudzi a mamy juz cala reszte zrobione to mozna sie pierdolic w logowanie
        // ale hasla sie wypierdalaja bardzo latwo wiec duzo roboty

    }

    @FXML
    void searchPressed(ActionEvent event) {
        vehiclesList.getItems().clear();
        String[] wordList = searchField.getText().toLowerCase().split(" ");// podzielenie tekstu na slowa
        HashMap<Vehicle, Integer> order = new HashMap<Vehicle, Integer>();
        for (Vehicle vehicle : vehicles) {
            int wordsMatched = getWordsMatched(vehicle, wordList);
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

    private static int getWordsMatched(Vehicle vehicle, String[] wordList) {
        int wordsMatched = 0; //
        for (String word : wordList) {
            if (vehicle.getMake().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getModel().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getYear().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getColor().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getType().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getPowertrain().toLowerCase().contains(word)) {
                wordsMatched++;
            }
        }
        return wordsMatched;
    }


}
