package org.example.terrificproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
// Write software that allows a rental company to manage a fleet of vehicles such as ICE/hybrid/BEV cars, motorcycles, pickups, and campers.
// The software should allow you to search for available vehicles in a given category,  make a reservation, and issue an invoice.


public class RentalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(org.example.terrificproject.RentalApplication.class.getResource("gemini.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rental Software");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        // najlatwiej bedzie chyba jak bedzie lista obiektow i w niej bedziemy przeszukiwac jak bedzei trzeba


    }

    private static void searchForVehicle(ArrayList<Vehicle> vehicles, String userInput) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getPowertrain().contains(userInput)) { // zaleznie od kategorii
                System.out.println(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + " " + vehicle.getColor() + " " + vehicle.getType() + " " + vehicle.getPowertrain());
            }
        }
    }

    private static void addingVehicles(ArrayList<Vehicle> vehicles) {
        Vehicle vehicle1 = new Vehicle("2018", "Toyota", "Corolla", "White", "Sedan", "ICE");
        Vehicle vehicle2 = new Vehicle("2022", "BMW", "Corolla", "Black", "Sedan", "Hybrid");
        Vehicle vehicle3 = new Vehicle("2019", "BMW", "Corolla", "Grey", "Sedan", "BEV");
        Vehicle vehicle4 = new Vehicle("2020", "Toyota", "Corolla", "Blue", "Sedan", "ICE");
        Vehicle vehicle5 = new Vehicle("2021", "Volvo", "Corolla", "Black", "Sedan", "Hybrid");

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
        // i tak dalej
    }
}