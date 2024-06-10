package org.example.terrificproject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ReserveController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;

    @FXML
    public void backPressed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("rental.fxml"));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void reserve(ActionEvent event) throws IOException{
        String name = nameField.getText();
        String surname = surnameField.getText();

    }
}
