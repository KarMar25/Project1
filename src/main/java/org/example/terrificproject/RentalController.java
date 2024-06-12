package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class RentalController implements Initializable {
    public static ArrayList<Vehicle> vehicles;
    private final String[] categoriesArray = {"ICE", "Hybrid", "Bev", "Motorcycles", "Pickups", "Campers", "Cars", "All"};
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
    private ChoiceBox<String> choiceBoxCategory;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addingVehicles();
            for (Vehicle vehicles : vehicles) {
                vehiclesList.getItems().add(new Text(vehicles.toString()));
            }

            colorPicker.setValue(null);
            ifVehicleChosenSwitchScenes();

            choiceBoxCategory.getItems().addAll(categoriesArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ifVehicleChosenSwitchScenes() {
        vehiclesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Text>() { // O KURWA DZIALA // JUZ NI
            @Override
            public void changed(ObservableValue<? extends Text> observableValue, Text oldText, Text newText) {
                if (newText != null) {
                    VehicleSceneController.selectedVehicle = getVehicleFromText(newText);
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
            }
        });

    }

    private Vehicle getVehicleFromText(Text text) {
        for (Vehicle vehicle : vehicles) {
            if (text.getText().equals(vehicle.toString())) {
                return vehicle;
            }
        }
        return null;
    }

    private void addingVehicles() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Vehicle.class, new VehicleAdapterFactory())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .setPrettyPrinting()
                .create();

        try (JsonReader jsonReader = new JsonReader(new FileReader("db/vehicles.json"))) {
            vehicles = gson.fromJson(jsonReader, new TypeToken<ArrayList<Vehicle>>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void loginPressed(ActionEvent event) {
        // jak sie komus bardzo nudzi a mamy juz cala reszte zrobione to mozna sie pierdolic w logowanie
        // ale hasla sie wypierdalaja bardzo latwo wiec duzo roboty

    }

    @FXML
    void searchPressed(ActionEvent event) {

        if (searchField.getText().isEmpty() && colorPicker.getValue() == null && choiceBoxCategory.getSelectionModel().getSelectedItem() == null) { // if search field is empty and no color is selected and no category is selected
            showAll();
            return;
        }

        vehiclesList.getItems().clear();

        Color selectedColor = colorPicker.getValue();
        String colorAsString = convertColorToString(selectedColor);
        String category = getCategory();

        String[] wordList = searchField.getText().toLowerCase().split(" ");

        if (colorAsString != null) {
            if (!colorAsString.equals("Unknown color")) { // if color is not null and is not unknown
                wordList = Arrays.copyOf(wordList, wordList.length + 1);
                wordList[wordList.length - 1] = colorAsString.toLowerCase();
            } else {
                vehiclesList.getItems().add(new Text("No vehicles found!"));
                return;
            }
        }
        if (category != null) {
            wordList = Arrays.copyOf(wordList, wordList.length + 1);
            wordList[wordList.length - 1] = category.toLowerCase();
        }

        wordList = Arrays.stream(wordList).distinct().toArray(String[]::new); // remove duplicates
        wordList = Arrays.stream(wordList).filter(s -> !s.isEmpty()).toArray(String[]::new); // remove empty strings

        HashMap<Vehicle, Integer> order = new HashMap<>(); // create a map to store the vehicles and the number of words matched
        for (Vehicle vehicle : vehicles) {
            int wordsMatched = getWordsMatched(vehicle, wordList);
            if (wordsMatched == 0) continue;
            order.put(vehicle, wordsMatched);
        }
        List<Map.Entry<Vehicle, Integer>> sortedEntries = order.entrySet().stream()
                .sorted(Map.Entry.<Vehicle, Integer>comparingByValue().reversed())
                .toList(); // sort the vehicles by the number of words matched

        for (Map.Entry<Vehicle, Integer> entry : sortedEntries) { // display vehicles in the correct order
            vehiclesList.getItems().add(new Text(entry.getKey().toString()));
        }

        if (vehiclesList.getItems().isEmpty()) {
            vehiclesList.getItems().add(new Text("No vehicles found!"));
        }
        ifVehicleChosenSwitchScenes();

    }

    private String getCategory() {
        String category = null;

        if (Objects.equals(choiceBoxCategory.getSelectionModel().getSelectedItem(), "Motorcycles")) {
            category = "Motorcycle";
        } else if (Objects.equals(choiceBoxCategory.getSelectionModel().getSelectedItem(), "Pickups")) {
            category = "Pickup";
        } else if (Objects.equals(choiceBoxCategory.getSelectionModel().getSelectedItem(), "Campers")) {
            category = "Camper";
        } else if (Objects.equals(choiceBoxCategory.getSelectionModel().getSelectedItem(), "Cars")) {
            category = "Car";
        } else if (choiceBoxCategory.getSelectionModel().getSelectedItem() != null) {
            category = choiceBoxCategory.getSelectionModel().getSelectedItem();
        }
        return category;
    }

    private void showAll() {
        vehiclesList.getItems().clear();
        for (Vehicle vehicle : vehicles) {
            vehiclesList.getItems().add(new Text(vehicle.toString()));
        }
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
        if (r == 255 && g == 192 && b == 203) return "Pink";
        if (r == 0 && g == 0 && b == 255) return "Blue";
        if (r == 0 && g == 255 && b == 0) return "Green";
        if (r == 255 && g == 0 && b == 255) return "Purple";
        if (r == 255 && g == 165 && b == 0) return "Orange";
        if (r == 255 && g == 215 && b == 0) return "Gold";
        if (r == 0 && g == 255 && b == 255) return "Cyan";

        return "Unknown color";
    }

}