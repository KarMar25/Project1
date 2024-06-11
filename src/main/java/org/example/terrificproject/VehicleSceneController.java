package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;


public class VehicleSceneController {
    public static Vehicle selectedVehicle; // wybrany pojazd z poprzedniego okna
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
    private Label dateFrom;
    @FXML
    private Label dateTo;

    @FXML
    private Text dateText;

    @FXML
    void reservePressed(ActionEvent event) throws IOException {
        if (datePickerFrom.getValue().isAfter(datePickerTo.getValue()) || datePickerTo.getValue().isBefore(datePickerFrom.getValue())) { // from has to be before to
            dateText.setText("Invalid date range");
            return;
        }
        if(datePickerFrom.getValue().isBefore(LocalDate.now()) || datePickerTo.getValue().isBefore(LocalDate.now())){ // before today
            dateText.setText("Choose a date in the future");
            return;
        }
        for (LocalDate date : selectedVehicle.getRentalDates()) {
            if (date.isAfter(datePickerFrom.getValue()) && date.isBefore(datePickerTo.getValue())) {
                dateText.setText("Vehicle is already reserved for this date range");
                return; // sprawdzanie czy pojazd jest dostepny w podanym przedziale dat
            }
        }
        for (int i = datePickerFrom.getValue().getDayOfYear(); i < datePickerTo.getValue().getDayOfYear(); i++) {
            selectedVehicle.getRentalDates().add(LocalDate.ofYearDay(datePickerFrom.getValue().getYear(), i));
            // add all dates between from and to, idk if it works
        }

        ReserveController.periodString = datePickerFrom.getValue().toString() + " to " + datePickerTo.getValue().toString();
        ReserveController.vehicleReservedString = selectedVehicle.toString();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reserve.fxml"))); // zmien scene na reserve
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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
    void initialize() {
        propertiesList.getItems().add("Year: " + selectedVehicle.getYear());
        propertiesList.getItems().add("Make: " + selectedVehicle.getMake());
        propertiesList.getItems().add("Model: " + selectedVehicle.getModel());
        propertiesList.getItems().add("Color: " + selectedVehicle.getColor());
        propertiesList.getItems().add("Type: " + selectedVehicle.getType());
        propertiesList.getItems().add("Powertrain: " + selectedVehicle.getPowertrain());
        vehicleImage.setImage(selectedVehicle.getImage());
        propertiesList.getItems().add("Rental dates: " + selectedVehicle.getRentalDates().toString()); // for testing purposes
    }
}


