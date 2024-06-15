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
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class ReserveController {
    protected static final String invoicesDataFilePath = "db/invoice.txt";
    public static Vehicle reservedVehicle;
    public static LocalDate dateFrom;
    public static LocalDate dateTo;
    public String periodString = dateFrom + " to " + dateTo;
    public double totalAmount = (ChronoUnit.DAYS.between(dateFrom, dateTo) + 1) * reservedVehicle.getPricePerDay();
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
    private TextField shippingAddress;

    @FXML
    public void initialize() {
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
        String ClientShippingAddress = shippingAddress.getText();
        //   Scanner scanner = new Scanner(Paths.get(invoicesDataFilePath));
        //  int invoiceNumber = scanner.nextInt(); a mialo być tak idealnie ale nie dziala
        int invoiceNumber = 1;

        InvoiceGenerator generator = new InvoiceGenerator();
        generator.printInvoice(
                "Terrific Rental Company",
                ClientName + " " + ClientSurname,
                ClientShippingAddress,
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

        changeScene(event);

    }

    private void changeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("final.fxml"))); // zmien scene na reserve
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
