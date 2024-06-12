package org.example.terrificproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


public class RentalController {

    public static ArrayList<Vehicle> vehicles; //  pojazdy
    @FXML
    private TextField searchField;

    @FXML
    private ListView<Text> vehiclesList;
    @FXML
    private Button exitButton;
    @FXML
    private ColorPicker colorPicker;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() throws IOException {
        addingVehicles();
        for (Vehicle vehicles : vehicles) {
            vehiclesList.getItems().add(new Text(vehicles.toString()));
        }
        colorPicker.setValue(null);

        ifVehicleChosenSwitchScenes();

    }

    private void ifVehicleChosenSwitchScenes() {
        vehiclesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Text>() { // O KURWA DZIALA // wybieranie pojazdu kliknieciem
            @Override
            public void changed(ObservableValue<? extends Text> observableValue, Text text, Text t1) {
                VehicleSceneController.selectedVehicle = vehicles.get(vehiclesList.getSelectionModel().getSelectedIndex());
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicleTemplate.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) vehiclesList.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }
        });
    }

    private void addingVehicles() throws IOException {

        // dla kazdego vehicle bedzie inny obrazek ale nie chcialo mi sie

        Gson gson = GsonProvider.createGson().newBuilder().setPrettyPrinting().create();
        JsonReader jsonReader = new JsonReader(new FileReader(("db/vehicles.json")));
        vehicles = gson.fromJson(jsonReader, new TypeToken<ArrayList<Vehicle>>(){}.getType());

    }

    @FXML
    void loginPressed(ActionEvent event) {
        // jak sie komus bardzo nudzi a mamy juz cala reszte zrobione to mozna sie pierdolic w logowanie
        // ale hasla sie wypierdalaja bardzo latwo wiec duzo roboty

    }

    @FXML
    void searchPressed(ActionEvent event) {
        if (searchField.getText().isEmpty() && colorPicker.getValue() == null) { // if search field is empty and no color is selected
            showAll();
            return;
        }
        vehiclesList.getItems().clear();
        String[] wordList = searchField.getText().toLowerCase().split(" ");// split the search field into words

        Color selectedColor = colorPicker.getValue();
        String colorAsString = convertColorToString(selectedColor);

        if(colorAsString != null) {
            if (colorAsString.equals("Unknown color")) {
                vehiclesList.getItems().add(new Text("No vehicles found!"));
                return;
            }
        }

        HashMap<Vehicle, Integer> order = new HashMap<Vehicle, Integer>();

        for (Vehicle vehicle : vehicles) {
            int wordsMatched = getWordsMatched(vehicle, wordList);
            if (wordsMatched == 0) continue; // if no words match do not add to the list
            if (colorAsString != null && !vehicle.getColor().equalsIgnoreCase(colorAsString))
                continue; // if color does not match do not add to the list
            order.put(vehicle, wordsMatched);
        }

        List<Map.Entry<Vehicle, Integer>> sortedEntries = order.entrySet().stream()
                .sorted(Map.Entry.<Vehicle, Integer>comparingByValue().reversed())
                .toList(); // sort the vehicles by the number of words matched

        for (Map.Entry<Vehicle, Integer> entry : sortedEntries) {
            vehiclesList.getItems().add(new Text(entry.getKey().toString()));// display vehicles in the correct order
        }

        if (vehiclesList.getItems().isEmpty()) {
            vehiclesList.getItems().add(new Text("No vehicles found!"));
        }
    }

    private void showAll() {
        vehiclesList.getItems().clear();
        for (Vehicle vehicle : vehicles) {
            vehiclesList.getItems().add(new Text(vehicle.toString()));
        }
    }

    private static int getWordsMatched(Vehicle vehicle, String[] wordList) {
        int wordsMatched = 0; //
        for (String word : wordList) {
            if (vehicle.getMake().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getModel().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getYear().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getColor().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getType().toLowerCase().contains(word)) {
                wordsMatched++;
            }
            if (vehicle.getPowertrain().toLowerCase().contains(word)) {
                wordsMatched++;
            }
        }
        return wordsMatched;
    }

    @FXML
    void exitPressed(ActionEvent event) {
        stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void anyColor(ActionEvent event) { // czyszczenie koloru
        colorPicker.setValue(null);
        searchPressed(event);
    }

    private String convertColorToString(Color color) {
        if (color == null) return null;
        String rgbColor = color.toString().substring(2, 8).toUpperCase();
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);

        if (r == 0 && g == 0 && b == 0) return "Black";
        if (r == 255 && g == 255 && b == 255) return "White";
        if (r == 255 && g == 0 && b == 0) return "Red";
        if (r == 255 && g == 255 && b == 0) return "Yellow";
        if (r == 128 && g == 128 && b == 128) return "Grey";
        if (r == 255 && g == 192 && b == 203) return "Pink"; //I am just a girl
        return "Unknown color";
    }


}