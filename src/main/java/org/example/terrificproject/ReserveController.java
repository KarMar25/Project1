package org.example.terrificproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ReserveController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static String periodString;
    public static String vehicleReservedString;

    public static String amountString;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;

    @FXML
    public Text period;

    @FXML
    public Text vehicleReserved;

    @FXML
    public Text totalAmount;
    protected static final String invoicesDataFilePath = "db/invoice.txt";
    @FXML
    public void initialize() {
        period.setText("For period from " + periodString);
        vehicleReserved.setText("You are reserving " + vehicleReservedString);
        totalAmount.setText("Total amount: " + amountString);

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
    public void reserve(ActionEvent event) throws IOException {
        String ClientName = nameField.getText();
        String ClientSurname = surnameField.getText();
        //   Scanner scanner = new Scanner(Paths.get(invoicesDataFilePath));
        //  int invoiceNumber = scanner.nextInt(); a mialo być tak idealnie ale nie dziala
        int invoiceNumber = 1;
        int amount;
        try {double amountInDollars = Double.parseDouble(amountString.replaceAll("[^0-9.]", ""));
            amount = (int) (amountInDollars);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            amount = 0;
        }
        InvoiceGenerator generator = new InvoiceGenerator();
        generator.printInvoice(
                "Terrific Rental Company",
                ClientName + " " + ClientSurname,
                "Shipping Address",
                "https://example.com/img/logo-invoice.png",
                invoiceNumber,
                "June 16, 2024",
                "Reservation Period",
                periodString,
                vehicleReservedString,
                1,
                amount
        );
    }
}
