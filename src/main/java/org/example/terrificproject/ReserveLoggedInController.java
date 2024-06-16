package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ReserveLoggedInController {
    public static Vehicle reservedVehicle;
    public static String periodString;
    public static double totalAmount;

    public static LocalDate dateFrom;
    public static LocalDate dateTo;
    @FXML
    public Text period;
    @FXML
    public Text vehicleReservedText;
    @FXML
    public Text totalAmountText;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField addressField;
    @FXML
    private Text errorText;

    @FXML
    public void initialize() {
        errorText.setText("");
        period.setText("For period from " + periodString);
        vehicleReservedText.setText("You are reserving " + reservedVehicle);
        totalAmountText.setText("Total amount: " + totalAmount + "$");

        nameField.setText(LoginController.loggedInUser.getName());
        surnameField.setText(LoginController.loggedInUser.getSurname());

    }

    @FXML
    void backPressed(ActionEvent event) {
        changeScene(event,"vehicleTemplate.fxml");


    }

    @FXML
    void rentPressed(ActionEvent event) throws IOException {
        String ClientName = nameField.getText();
        String ClientSurname = surnameField.getText();
        String ClientAddress = addressField.getText();
        if(ClientName.isEmpty() || ClientSurname.isEmpty() || ClientAddress.isEmpty()){
            errorText.setText("All fields must be filled");
            return;
        }
        int invoiceNumber = 1;

        InvoiceGenerator generator = new InvoiceGenerator();
        generator.printInvoice(
                "Terrific Rental Company",
                ClientName + " " + ClientSurname,
                ClientAddress,
                "https://example.com/img/logo-invoice.png",
                invoiceNumber,
                "June 16, 2024",
                "Reservation Period",
                periodString,
                reservedVehicle.toString(),
                1,
                (int) totalAmount
        );

        for (int i =dateFrom.getDayOfYear() ; i <= dateTo.getDayOfYear(); i++) { // add all dates between from and to
            for (Vehicle vehicle : RentalController.vehicles) {
                if (vehicle.equals(reservedVehicle)) { // if the vehicle is the one we are reserving
                    vehicle.getRentalDates().add(LocalDate.ofYearDay(dateFrom.getYear(), i)); // add the date to the list of reserved dates
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


        HashMap<String, ArrayList<LocalDate>> reservedVehicleMap = LoginController.loggedInUser.getRentedVehicles();
        ArrayList<LocalDate> dates = new ArrayList<>();
        for (int i = dateFrom.getDayOfYear(); i <= dateTo.getDayOfYear(); i++) {
            dates.add(LocalDate.ofYearDay(dateFrom.getYear(), i));
        } // add all dates between from and to

        reservedVehicleMap.put(String.valueOf(reservedVehicle), dates);

        LoginController.loggedInUser.setRentedVehicles(reservedVehicleMap);

        Gson gson2 = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();
        FileWriter file2 = new FileWriter("db/users.json");
        file2.write(gson2.toJson(LoginController.users));
        file2.close(); // close the file after saving the changes


        changeScene(event,"final.fxml");



    }

    private void changeScene(ActionEvent event, String fxmlFile) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
