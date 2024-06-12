package org.example.terrificproject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

// Write software that allows a rental company to manage a fleet of vehicles such as ICE/hybrid/BEV cars, motorcycles, pickups, and campers.
// The software should allow you to search for available vehicles in a given category,  make a reservation, and issue an invoice.

// fx jest napisany zeby dzialal, jak ktos chce to moze upiekniac
public class RentalApplication extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(org.example.terrificproject.RentalApplication.class.getResource("rental.fxml"));
            Scene mainScene = new Scene(fxmlLoader.load());
            stage.setTitle("Rental Software");
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.showAndWait();

        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}