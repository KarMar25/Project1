package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private Text dateText;

    @FXML
    void reservePressed(ActionEvent event) throws IOException {
        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null) { // if the date is not selected
            dateText.setText("Select a date");
            return;
        }
        if (datePickerFrom.getValue().isAfter(datePickerTo.getValue()) || datePickerTo.getValue().isBefore(datePickerFrom.getValue())) { // from has to be before to
            dateText.setText("Invalid date range");
            return;
        }
        if (datePickerFrom.getValue().isBefore(LocalDate.now()) || datePickerTo.getValue().isBefore(LocalDate.now())) { // before today
            dateText.setText("Choose a date in the future");
            return;
        }
        for (LocalDate date : selectedVehicle.getRentalDates()) {
            if (date.isAfter(datePickerFrom.getValue()) && date.isBefore(datePickerTo.getValue()) || date.isEqual(datePickerFrom.getValue()) || date.isEqual(datePickerTo.getValue())) {
                dateText.setText("Vehicle is already reserved for this date range");
                return; // checks if the vehicle is already reserved for this date range
            }
        }
        for (int i = datePickerFrom.getValue().getDayOfYear(); i <= datePickerTo.getValue().getDayOfYear(); i++) { // add all dates between from and to
            for (Vehicle vehicle : RentalController.vehicles) {
                if (vehicle.equals(selectedVehicle)) { // if the vehicle is the one we are reserving
                    System.out.println("Vehicle: " + vehicle.getRentalDates().toString());
                    vehicle.getRentalDates().add(LocalDate.ofYearDay(datePickerFrom.getValue().getYear(), i)); // add the date to the list of reserved dates
                }
            }

        }


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Vehicle.class, new VehicleAdapterFactory())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();
        FileWriter file = new FileWriter("db/vehicles.json");
        file.write(gson.toJson(RentalController.vehicles));
        file.close(); // close the file after saving the changes

        updateReserveScene();

        changeScene("reserve.fxml", event);

    }

    private void changeScene(String name, ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(name))); // zmien scene na reserve
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void updateReserveScene() {
        ReserveController.periodString = datePickerFrom.getValue().toString() + " to " + datePickerTo.getValue().toString();
        ReserveController.vehicleReservedString = selectedVehicle.toString();
        long daysBetween = ChronoUnit.DAYS.between(datePickerFrom.getValue(), datePickerTo.getValue());
        double totalAmount = daysBetween * selectedVehicle.getPricePerDay();
        ReserveController.vehicleReservedString = selectedVehicle.toString() + " for " + daysBetween + " days. Total amount: $" + totalAmount;
    }

    @FXML
    void backPressed(ActionEvent event) throws IOException {
        changeScene("rental.fxml", event);
    }

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
        propertiesList.getItems().add("Unavailable dates: " + selectedVehicle.getRentalDates()); // testing

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

        String imagePath = selectedVehicle.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) { // sprawdzamy czy sciezka jest poprawna vo sie wyjebuje co chwile
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            if (vehicleImage != null) {
                vehicleImage.setImage(image);
            } else {
                System.out.println("vehicleImage is null.");
            }
        } else {
            // Handle missing image scenario
            System.out.println("Image path is invalid or empty.");
        }
    }
}


