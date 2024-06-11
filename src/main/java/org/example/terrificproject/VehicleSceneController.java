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
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;


public class VehicleSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static Vehicle selectedVehicle; // wybrany pojazd z poprzedniego okna

    @FXML
    public DatePicker datePickerFrom;

    @FXML
    public DatePicker datePickerTo;

    @FXML
    private ListView<String> propertiesList;

    @FXML
    private ImageView vehicleImage;
    @FXML
    private Label dateFrom;
    @FXML
    private Label dateTo;

    @FXML
    void reservePressed(ActionEvent event) {
        for(int i = 0; i < selectedVehicle.getRentalDates().size(); i++){
            if(datePickerFrom.getValue().isAfter(selectedVehicle.getRentalDates().get(i)) || datePickerTo.getValue().isBefore(selectedVehicle.getRentalDates().get(i))){
                // show error message
               dateFrom.setText(datePickerFrom.toString());
                return;
            }
        }
        // also trzeba sprawdzac ktory dzisiaj zeby nie mozna bylo rezerwowac pojazdu dzisiaj i w przeszlosci
        for(int i = datePickerFrom.getValue().getDayOfYear(); i < datePickerTo.getValue().getDayOfYear(); i++){
            selectedVehicle.getRentalDates().add(LocalDate.ofYearDay(datePickerFrom.getValue().getYear(), i));
            dateTo.setText(datePickerTo.toString());
            // add all dates between from and to
        }  // nwm czy dziala
    }
    @FXML
    void backPressed(ActionEvent event) throws IOException{
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
     }
    }


