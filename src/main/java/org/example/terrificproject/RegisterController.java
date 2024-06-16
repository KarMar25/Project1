package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text errorText;

    private Stage stage;

    @FXML
    void initialize() {
        errorText.setText("");
    }

    @FXML
    void registerPressed(ActionEvent event) {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

// validate the input
        if (!usernameTaken(username)) {
            errorText.setText("Username is already taken!");
            return;
        } else if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorText.setText("All fields must be filled");
            return;
        } else if (!passwordIsValid(password)) {
            return;
        } else if (!username.matches("^[a-zA-Z0-9]*$")) {
            errorText.setText("Username must contain only letters and numbers");
            return;
        } else if (username.length() < 6) {
            errorText.setText("Username must be at least 6 characters long");
            return;
        } else if (name.length() > 20 || surname.length() > 20 || username.length() > 20 || password.length() > 20) {
            errorText.setText("All fields must be less than 20 characters long");
            return;
        } else if (!name.matches("^[a-zA-Z]*$") || !surname.matches("^[a-zA-Z]*$")) {
            errorText.setText("Invalid name or surname! Only letters are allowed.");
            return;
        } else if (name.length() < 2 || surname.length() < 2) {
            errorText.setText("Name and surname must be at least 2 characters long");
            return;
        }


        User user = new User(name, surname, username, password, new HashMap<>());
        saveTheUser(user);

        errorText.setText("User registered successfully! You can now go back to login.");
        nameField.clear();
        surnameField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    public static void saveTheUser(User user) {

        File file = new File("db/users.json");
        ArrayList<User> users = new ArrayList<>();

        if (file.exists() && file.length() != 0) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();
                users = gson.fromJson(reader, userListType); // get the users from the file
                if (users == null) {
                    users = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        users.add(user); // add the new user to the list


        try (FileWriter writer = new FileWriter("db/users.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).setPrettyPrinting().create();
            gson.toJson(users, writer); // save the list back to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean passwordIsValid(String password) {
        if (password.length() < 6) {
            errorText.setText("Password must be at least 6 characters long");
            return false;
        } else if (password.equals(password.toLowerCase())) {
            errorText.setText("Password must contain at least one uppercase letter");
            return false;
        } else if (password.equals(password.toUpperCase())) {
            errorText.setText("Password must contain at least one lowercase letter");
            return false;
        } else if (!password.matches(".*\\d.*")) {
            errorText.setText("Password must contain at least one digit");
            return false;
        }
        return true;
    }

    private boolean usernameTaken(String username) {
        for (User user : LoginController.users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private void switchScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backPressed(ActionEvent event) throws IOException {
        errorText.setText("");
        switchScene();
    }
}

