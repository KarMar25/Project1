package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class VehicleSceneController {
    public static Vehicle selectedVehicle;
    @FXML
    public DatePicker datePickerFrom;
    @FXML
    public DatePicker datePickerTo;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ListView<String> propertiesList;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Text dateText;

    @FXML
    void initialize() {
        propertiesList.getItems().add("Price per day: " + selectedVehicle.getPricePerDay() + "$");
        propertiesList.getItems().add("Year: " + selectedVehicle.getYear());
        propertiesList.getItems().add("Make: " + selectedVehicle.getMake());
        propertiesList.getItems().add("Model: " + selectedVehicle.getModel());
        propertiesList.getItems().add("Color: " + selectedVehicle.getColor());
        propertiesList.getItems().add("Type: " + selectedVehicle.getType());
        propertiesList.getItems().add("Powertrain: " + selectedVehicle.getPowertrain());
        propertiesList.getItems().add("VIN: " + selectedVehicle.getVin());
        propertiesList.getItems().add("Mileage: " + selectedVehicle.getMileage());


        if (selectedVehicle instanceof Motorcycle) {
            propertiesList.getItems().add("Has sidecar: " + ((Motorcycle) selectedVehicle).hasSidecar());
            propertiesList.getItems().add("Top speed: " + ((Motorcycle) selectedVehicle).getTopSpeed());
            propertiesList.getItems().add("ABS: " + ((Motorcycle) selectedVehicle).isAbs());
        }
        if (selectedVehicle instanceof Camper) {
            propertiesList.getItems().add("Number of beds: " + ((Camper) selectedVehicle).getNumberOfBeds());
            propertiesList.getItems().add("Has bathroom: " + ((Camper) selectedVehicle).isHasBathroom());
        }
        if (selectedVehicle instanceof Pickup) {
            propertiesList.getItems().add("Has tow hitch: " + ((Pickup) selectedVehicle).getLoadCapacity());
        }
        if (selectedVehicle instanceof Car) {
            propertiesList.getItems().add("Number of doors: " + ((Car) selectedVehicle).getDoorCount());
            propertiesList.getItems().add("Trunk capacity: " + ((Car) selectedVehicle).getTrunkCapacity());
        }
        if (selectedVehicle instanceof Tractor) {
            propertiesList.getItems().add("Type of cab: " + ((Tractor) selectedVehicle).getCabType());
            propertiesList.getItems().add("Trunk capacity: " + ((Tractor) selectedVehicle).getTrunkCapacity());
        }

        propertiesList.getItems().add("Unavailable dates: " + selectedVehicle.getRentalDates());

        String imagePath = selectedVehicle.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            if (vehicleImage != null) {
                vehicleImage.setImage(image);
            }
        }


    }

    @FXML
    void reservePressed(ActionEvent event) throws IOException {
        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null) {
            dateText.setText("Select a date");
            return;
        }
        if (datePickerFrom.getValue().isAfter(datePickerTo.getValue()) || datePickerTo.getValue().isBefore(datePickerFrom.getValue())) {
            dateText.setText("Invalid date range");
            return;
        }
        if (datePickerFrom.getValue().isBefore(LocalDate.now()) || datePickerTo.getValue().isBefore(LocalDate.now())) {
            dateText.setText("Choose a date in the future");
            return;
        }
        for (LocalDate date : selectedVehicle.getRentalDates()) {
            if (date.isAfter(datePickerFrom.getValue()) && date.isBefore(datePickerTo.getValue()) || date.isEqual(datePickerFrom.getValue()) || date.isEqual(datePickerTo.getValue())) {
                dateText.setText("Vehicle is already reserved for this date range");
                return;
            }
        }


        updateReserveScene();
        updateReserveLoggedInScene();

        if (RentalController.loggedInUser == null) {
            changeScene("reserve.fxml", event);

        }
        if (RentalController.loggedInUser != null) {
            changeScene("reserveLoggedIn.fxml", event);

        }


    }

    private void updateReserveLoggedInScene() {
        ReserveLoggedInController.reservedVehicle = selectedVehicle;
        ReserveLoggedInController.dateFrom = datePickerFrom.getValue();
        ReserveLoggedInController.dateTo = datePickerTo.getValue();
        ReserveLoggedInController.periodString = datePickerFrom.getValue() + " to " + datePickerTo.getValue();
        ReserveLoggedInController.totalAmount = (ChronoUnit.DAYS.between(datePickerFrom.getValue(), datePickerTo.getValue()) + 1) * selectedVehicle.getPricePerDay();
    }

    private void changeScene(String name, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(name))); // zmien scene na reserve
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void updateReserveScene() {
        ReserveController.reservedVehicle = selectedVehicle;
        ReserveController.dateFrom = datePickerFrom.getValue();
        ReserveController.dateTo = datePickerTo.getValue();
        ReserveController.periodString = datePickerFrom.getValue() + " to " + datePickerTo.getValue();
        ReserveController.totalAmount = (ChronoUnit.DAYS.between(datePickerFrom.getValue(), datePickerTo.getValue()) + 1) * selectedVehicle.getPricePerDay();
    }

    @FXML
    void backPressed(ActionEvent event) throws IOException {
        changeScene("rental.fxml", event);
    }


}


