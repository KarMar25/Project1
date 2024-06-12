package org.example.terrificproject;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class RentalController implements Initializable {

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
    Label category;
    @FXML
    private ChoiceBox<String> choiceBoxCategory;
    private final String[] categoriesArray = {"ICE", "Hybrid", "Bev", "Motorcycles", "Pickups", "Campers", "Cars"};

    public ArrayList<Vehicle> vehicles = new ArrayList<>(); // baza danych pojazdow

//    @FXML
//    public void drawList() throws IOException {
//        addingVehicles();
//        for (Vehicle vehicles : vehicles) {
//            vehiclesList.getItems().add(new Text(vehicles.toString()));
//        }
//    }

    private void addingVehicles() throws IOException {
        ArrayList<LocalDate> rentalDates = new ArrayList<LocalDate>();
        // dla kazdego vehicle bedzie inny date i obrazek ale nie chcialo mi sie
        // trzeba tez te vehicles zrobic w jsonie, zeby rezerwacja nadpisywala plik
        // dzieki czemu po kazdym otwarciu nie bedzie sie zerowac
        // albo ogolnie zeby rezerwacje byly zapisywane w pliku


        Image image = new Image("file:src/main/resources/org/example/terrificproject/miata.jpg");
        Vehicle vehicle1 = new Vehicle("2021", "Toyota", "Corolla", "Black", "Sedan", "ICE", true, rentalDates, image);
        Vehicle vehicle2 = new Vehicle("2021", "Mazda", "Miata", "Red", "Convertible", "ICE", true, rentalDates, image);
        Vehicle vehicle3 = new Vehicle("2021", "Ford", "F-150", "White", "Pickup", "ICE", true, rentalDates, image);
        Vehicle vehicle4 = new Vehicle("2021", "Harley-Davidson", "Road King", "Black", "Motorcycle", "ICE", true, rentalDates, image);
        Vehicle vehicle5 = new Vehicle("2021", "Winnebago", "Revel", "White", "Camper", "ICE", true, rentalDates, image);

        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
    }

    @FXML
    void loginPressed(ActionEvent event) {
        // jak sie komus bardzo nudzi a mamy juz cala reszte zrobione to mozna sie pierdolic w logowanie
        // ale hasla sie wypierdalaja bardzo latwo wiec duzo roboty

    }

    @FXML
    void searchPressed(ActionEvent event) {
        vehiclesList.getItems().clear();
        String[] wordList = searchField.getText().toLowerCase().split(" ");// podzielenie tekstu na slowa

        Color selectedColor = colorPicker.getValue();
        String colorAsString = convertColorToString(selectedColor);

        HashMap<Vehicle, Integer> order = new HashMap<Vehicle, Integer>();
        for (Vehicle vehicle : vehicles) {
            int wordsMatched = getWordsMatched(vehicle, wordList);
            if (wordsMatched == 0) continue;
            if (colorAsString != null && !vehicle.getColor().equalsIgnoreCase(colorAsString)) continue;
            order.put(vehicle, wordsMatched);
        }

        List<Map.Entry<Vehicle, Integer>> sortedEntries = order.entrySet().stream()
                .sorted(Map.Entry.<Vehicle, Integer>comparingByValue().reversed())
                .toList();

        for (Map.Entry<Vehicle, Integer> entry : sortedEntries) {
            vehiclesList.getItems().add(new Text(entry.getKey().toString()));
        }
    }

    @FXML
    void exitPressed(ActionEvent event) {
        stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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

    public void setColorPicker(ActionEvent event) {
        Color color = colorPicker.getValue();
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
        return null;
    }

    public void switchReserveScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reserve.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //zmiana sceny jedna for now ale ogarnięte jak zrobić
    }

    public void switchVehicleScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicleTemplate.fxml")));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addingVehicles();
            for (Vehicle vehicles : vehicles) {
                vehiclesList.getItems().add(new Text(vehicles.toString()));
            }
            choiceBoxCategory.getItems().addAll(categoriesArray);
            choiceBoxCategory.setOnAction(this::printSetCategory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void printSetCategory(ActionEvent event) {
        String chosenCategory = choiceBoxCategory.getValue();
        category.setText(chosenCategory);
    }
}
