package org.example.terrificproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
// Write software that allows a rental company to manage a fleet of vehicles such as ICE/hybrid/BEV cars, motorcycles, pickups, and campers.
// The software should allow you to search for available vehicles in a given category,  make a reservation, and issue an invoice.


public class RentalApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(org.example.terrificproject.RentalApplication.class.getResource("rental.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        stage.setTitle("Rental Software");
        stage.setScene(mainScene);
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
    }