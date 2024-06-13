package org.example.terrificproject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField textMessage;

    @FXML
    private Button loginButt;
    @FXML
    private PasswordField passwordField;
    private Stage stage;
    private Scene scene;

    @FXML
    void loginSuccess(ActionEvent event) throws IOException {
        System.out.println("Button clicked");
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (password.equals("Terrific")) {
                showLoginSuccessScene();
            } else {
                showLoginFailedScene();
            }
        }

        private void showLoginSuccessScene() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rental.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

        private void showLoginFailedScene() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginUnsuccessful.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

}