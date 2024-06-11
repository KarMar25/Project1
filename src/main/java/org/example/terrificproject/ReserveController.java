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
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;

    @FXML
    public Text period;

    @FXML
    public Text vehicleReserved;

    @FXML
    public void initialize() {
        period.setText("For period from " + periodString);
        vehicleReserved.setText("You are reserving \"" + vehicleReservedString + "\"");
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
    public void reserve(ActionEvent event) throws IOException{

        String name = nameField.getText();
        String surname = surnameField.getText();
        // invoice

    }
}
