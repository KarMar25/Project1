package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginUnsuccessfulController {
    @FXML
    private Button againButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void exit(ActionEvent event) throws IOException {



            FXMLLoader loader = new FXMLLoader(getClass().getResource("login1.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage = new Stage(); // Nowe okno dla sceny logowania
            stage.setScene(scene);
            stage.show();

            // Zamykanie okna informującego o nieudanym logowaniu
            stage = (Stage) againButton.getScene().getWindow();
            stage.close();
        }
    }

