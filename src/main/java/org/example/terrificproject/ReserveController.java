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
import java.util.Objects;

public class ReserveController {
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

    private static void overwriteFile() throws IOException { // saves the changes to the file
        Gson gson = new GsonBuilder().registerTypeAdapter(Vehicle.class, new VehicleAdapterFactory()).registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
        FileWriter file = new FileWriter("db/vehicles.json");
        file.write(gson.toJson(RentalController.vehicles));
        file.close();
    }

    private static void generateInvoice(String ClientName, String ClientSurname, String ClientAddress) {
        int invoiceNumber = 1;

        InvoiceGenerator generator = new InvoiceGenerator();
        generator.printInvoice("Terrific Rental Company", ClientName + " " + ClientSurname, ClientAddress, "https://example.com/img/logo-invoice.png", invoiceNumber, "June 16, 2024", "Reservation Period", periodString, reservedVehicle.toString(), 1, (int) totalAmount);

        for (int i = dateFrom.getDayOfYear(); i <= dateTo.getDayOfYear(); i++) {
            for (Vehicle vehicle : RentalController.vehicles) {
                if (vehicle.equals(reservedVehicle)) {
                    vehicle.getRentalDates().add(LocalDate.ofYearDay(dateFrom.getYear(), i));
                }
            }

        }
    }

    @FXML
    public void initialize() {
        errorText.setText("");
        period.setText("For period from " + periodString);
        vehicleReservedText.setText("You are reserving " + reservedVehicle);
        totalAmountText.setText("Total amount: " + totalAmount + "$");
    }

    @FXML
    public void backPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("rental.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void rent(ActionEvent event) throws IOException {
        String ClientName = nameField.getText();
        String ClientSurname = surnameField.getText();
        String ClientAddress = addressField.getText();

        if (ClientName.isEmpty() || ClientSurname.isEmpty() || ClientAddress.isEmpty()) {
            errorText.setText("All fields must be filled");
            return;
        }


        generateInvoice(ClientName, ClientSurname, ClientAddress);

        overwriteFile();

        changeScene(event);

    }

    @FXML
    private void loginPressed(ActionEvent event) throws IOException {
        LoginController.whileRenting = true;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void changeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("final.fxml"))); // zmien scene na reserve
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
