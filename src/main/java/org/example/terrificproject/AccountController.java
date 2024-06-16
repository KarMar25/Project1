package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AccountController {

    private final User user = LoginController.loggedInUser;
    @FXML
    private TreeView<String> reservations;
    @FXML
    private Text errorText;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void initialize() {

        errorText.setText("");
        HashMap<String, ArrayList<LocalDate>> rentedVehicles = user.getRentedVehicles();

        TreeItem<String> reservation = new TreeItem<>("");
        reservation.setExpanded(true); // expand the tree view (show the reservations)

        if (rentedVehicles == null || rentedVehicles.isEmpty()) {
            reservation.getChildren().add(new TreeItem<>("No reservations yet"));
        } else { // add the reservations to the tree view
            for (String vehicle : rentedVehicles.keySet()) {
                TreeItem<String> vehicleItem = new TreeItem<>(vehicle);
                reservation.getChildren().add(vehicleItem);
                for (LocalDate date : rentedVehicles.get(vehicle)) {
                    TreeItem<String> dateItem = new TreeItem<>(date.toString());
                    vehicleItem.getChildren().add(dateItem);
                }
            }
        }
        reservations.setRoot(reservation);

    }

    @FXML
    void backPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rental.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void cancelPressed(ActionEvent event) {
        TreeItem<String> selectedItem = reservations.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;
        if (selectedItem.getParent() == null || selectedItem.isLeaf()) { // if the selected item is a leaf or the root (a date or no reservation)
            errorText.setText("Select a vehicle to cancel");
            return;
        }

        String vehicle = selectedItem.getValue();
        HashMap<String, ArrayList<LocalDate>> rentedVehicles = user.getRentedVehicles();

        for (Vehicle v : RentalController.vehicles) { // remove the rental dates from the vehicle
            for (LocalDate date : rentedVehicles.get(vehicle)) {
                v.getRentalDates().remove(date);
            }
        }

        rentedVehicles.remove(vehicle); // remove the vehicle from the user's rented vehicles
        user.setRentedVehicles(rentedVehicles); // update the rented vehicles

        try {
            FileWriter file = new FileWriter("db/users.json");
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
            file.write(gson.toJson(LoginController.users)); // save the changes to the file
            file.close();

            FileWriter file2 = new FileWriter("db/vehicles.json");
            Gson gson2 = new GsonBuilder().registerTypeAdapter(Vehicle.class, new VehicleAdapterFactory()).registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
            file2.write(gson2.toJson(RentalController.vehicles)); // save the changes to the file
            file2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize(); // refresh the tree view
        errorText.setText("Reservation cancelled successfully");


    }
}


