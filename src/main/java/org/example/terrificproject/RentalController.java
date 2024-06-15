package org.example.terrificproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class RentalController implements Initializable {
    public static final int PRICE_LOW = 85;
    public static final int PRICE_MAX = 230;
    public static ArrayList<Vehicle> vehicles;
    public static User loggedInUser;

    @FXML
    private TextField searchField;
    @FXML
    private ListView<Text> vehiclesList;
    @FXML
    private Button exitButton;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private CheckBox bevCheck;
    @FXML
    private CheckBox camperCheck;
    @FXML
    private CheckBox carCheck;
    @FXML
    private CheckBox hybridCheck;
    @FXML
    private CheckBox iceCheck;
    @FXML
    private CheckBox motorcycleCheck;
    @FXML
    private CheckBox pickupCheck;
    @FXML
    private Slider priceFromSlider;
    @FXML
    private Slider priceToSlider;
    @FXML
    private Spinner<String> yearSpin;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ChoiceBox<String> choiceBoxSortBy;

    @FXML
    private Text loginInfo;
    @FXML
    private Button loginButton;

    private static ArrayList<String> getYearsArray() {
        ArrayList<String> years = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (!years.contains(vehicle.getYear())) {
                years.add(vehicle.getYear());
            }
        }
        years.sort(Comparator.comparingInt(Integer::parseInt));
        years.add("Choose year");
        return years;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            addingVehicles();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        colorPicker.setValue(null);

        choiceBoxSortBy.getItems().addAll("Price increasing", "Price decreasing", "Year increasing", "Year decreasing", "Relevance");
        choiceBoxSortBy.setValue("Sort by"); // default value

        priceFromSlider.setMin(PRICE_LOW);
        priceFromSlider.setMax(PRICE_MAX);
        priceToSlider.setMin(PRICE_LOW);
        priceToSlider.setMax(PRICE_MAX);

        priceToSlider.setValue(PRICE_MAX);
        priceFromSlider.setValue(PRICE_LOW);

        ArrayList<String> years = getYearsArray();

        yearSpin.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(years)));
        yearSpin.getValueFactory().setValue("Choose year"); // default value

        Platform.runLater(this::ifVehicleChosenSwitchScenes); // switch scenes when vehicle is chosen
        yearSpin.valueProperty().addListener((obs, oldValue, newValue) -> searchPressed(new ActionEvent())); // update search results when year changes
        priceFromSlider.valueProperty().addListener((obs, oldValue, newValue) -> searchPressed(new ActionEvent())); // update search results when price changes
        priceToSlider.valueProperty().addListener((obs, oldValue, newValue) -> searchPressed(new ActionEvent())); // update search results when price changes

        showAll();
        if(loggedInUser != null){
            loginInfo.setText("Logged in as: " + LoginController.loggedInUser.getUsername());
            loginButton.setText("Log out");
        }
        else{
            loginInfo.setText("Not logged in");
        }

    }

    private void ifVehicleChosenSwitchScenes() {
        Platform.runLater(() -> {
            vehiclesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Text>() {
                @Override
                public void changed(ObservableValue<? extends Text> observableValue, Text oldText, Text newText) {
                    if (newText != null) {
                        Vehicle selectedVehicle = getVehicleFromText(newText);
                        if (selectedVehicle == null) {
                            return; // Do nothing if the selected item is not a valid vehicle
                        }
                        VehicleSceneController.selectedVehicle = selectedVehicle;
                        try {
                            Stage stage = (Stage) vehiclesList.getScene().getWindow();
                            if (stage == null) {
                                return;
                            }
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("vehicleTemplate.fxml")));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        });
    }


    private Vehicle getVehicleFromText(Text text) {
        String textString = text.getText();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.toString().equals(textString)) {
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
        try {
            if(loggedInUser != null){
                loggedInUser = null;
                loginInfo.setText("Not logged in");
                loginButton.setText("Log in");
            }
            else{
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
                stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void sortBy(ActionEvent actionEvent) {
        if (vehiclesList.getItems().size() == 1) { // no vehicles found
            return;
        }

        List<String> listItems = new ArrayList<>();
        for (Text text : vehiclesList.getItems()) {
            listItems.add(text.getText());
        }

        vehiclesList.getItems().clear();

        List<String> stringMatches = new ArrayList<>();
        List<String> stringPartialMatches = new ArrayList<>();

        boolean isPartial = false;
        for (String item : listItems) {
            if (item.equals("These are all the exact matches found!") || item.equals("No exact matches found!")) {
                isPartial = true;
                continue;
            }
            if (isPartial) {
                stringPartialMatches.add(item);
            } else {
                stringMatches.add(item);
            }
        }

        String selected = choiceBoxSortBy.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        List<Vehicle> vehicleMatches = new ArrayList<>();
        List<Vehicle> vehiclePartialMatches = new ArrayList<>();

        for (String sortedMatch : stringMatches) {
            for (Vehicle vehicle : vehicles) {
                if (sortedMatch.equals(vehicle.toString())) {
                    vehicleMatches.add(vehicle);
                }
            }
        }
        for (String sortedMatch : stringPartialMatches) {
            for (Vehicle vehicle : vehicles) {
                if (sortedMatch.equals(vehicle.toString())) {
                    vehiclePartialMatches.add(vehicle);
                }
            }
        }


        if (selected.equals("Relevance") || selected.equals("Sort by")) {
            String[] wordList = searchField.getText().toLowerCase().split(" ");
            Comparator<Vehicle> relevanceComparator = (v1, v2) -> Integer.compare(getWordsMatched(v2, wordList), getWordsMatched(v1, wordList));

            vehicleMatches.sort(relevanceComparator);
            vehiclePartialMatches.sort(relevanceComparator);
        } else if (selected.equals("Price increasing")) {
            vehicleMatches.sort(Comparator.comparing(Vehicle::getPricePerDay));
            vehiclePartialMatches.sort(Comparator.comparing(Vehicle::getPricePerDay));
        } else if (selected.equals("Price decreasing")) {
            vehicleMatches.sort(Comparator.comparing(Vehicle::getPricePerDay).reversed());
            vehiclePartialMatches.sort(Comparator.comparing(Vehicle::getPricePerDay).reversed());
        } else if (selected.equals("Year increasing")) {
            vehicleMatches.sort(Comparator.comparing(Vehicle::getYear));
            vehiclePartialMatches.sort(Comparator.comparing(Vehicle::getYear));
        } else if (selected.equals("Year decreasing")) {
            vehicleMatches.sort(Comparator.comparing(Vehicle::getYear).reversed());
            vehiclePartialMatches.sort(Comparator.comparing(Vehicle::getYear).reversed());
        }

        if (!vehicleMatches.isEmpty()) {
            vehicleMatches.forEach(vehicle -> vehiclesList.getItems().add(new Text(vehicle.toString())));
            if (!vehiclePartialMatches.isEmpty()) {
                vehiclesList.getItems().add(new Text("These are all the exact matches found!"));
                vehiclesList.getItems().add(new Text("Here are some vehicles that partially match your search:"));
            }
            vehiclePartialMatches.forEach(vehicle -> vehiclesList.getItems().add(new Text(vehicle.toString())));
        } else {
            vehiclesList.getItems().add(new Text("No exact matches found!"));
            if (!vehiclePartialMatches.isEmpty()) {
                vehiclesList.getItems().add(new Text("Here are some vehicles that partially match your search:"));
            }
            vehiclePartialMatches.forEach(vehicle -> vehiclesList.getItems().add(new Text(vehicle.toString())));
        }
    }

    @FXML
    void searchPressed(ActionEvent event) {
        if (isSearchCriteriaEmpty()) {
            showAll();
            return;
        }

        vehiclesList.getItems().clear();
        List<Vehicle> filteredVehicles = vehicles;

        filteredVehicles = filterByColor(filteredVehicles);
        filteredVehicles = filterByPrice(filteredVehicles);
        filteredVehicles = filterByType(filteredVehicles);
        filteredVehicles = filterByPowertrain(filteredVehicles);
        filteredVehicles = filterBySearchText(filteredVehicles);
        filteredVehicles = filterByYear(filteredVehicles);

        String[] wordList = searchField.getText().toLowerCase().split(" ");
        List<Vehicle> partialMatches = new ArrayList<>();

        for (Vehicle vehicle : filteredVehicles) {
            vehiclesList.getItems().add(new Text(vehicle.toString()));
        }


        for (Vehicle vehicle : vehicles) {
            int wordsMatched = getWordsMatched(vehicle, wordList);
            if (wordsMatched > 0 && wordsMatched < wordList.length) {
                partialMatches.add(vehicle);
            }
        }

        if (vehiclesList.getItems().isEmpty() && partialMatches.isEmpty()) {
            vehiclesList.getItems().add(new Text("No vehicles found!"));
        } else if (!vehiclesList.getItems().isEmpty() && !partialMatches.isEmpty()) {
            Text text = new Text("These are all the exact matches found!");
            text.setFont(Font.font("Calibre", 12));
            text.setStyle("<font-weight>: italic;"); // whatever we want
            Text text2 = new Text("Here are some vehicles that partially match your search:");
            text2.setFont(Font.font("Calibre", 12));
            text2.setStyle("<font-weight>: italic;");
            vehiclesList.getItems().add(text);
            vehiclesList.getItems().add(text2);
            partialMatches.sort((v1, v2) -> Integer.compare(getWordsMatched(v2, wordList), getWordsMatched(v1, wordList)));
            partialMatches.forEach(vehicle -> vehiclesList.getItems().add(new Text(vehicle.toString())));
        } else if (vehiclesList.getItems().isEmpty() && !partialMatches.isEmpty()) {
            vehiclesList.getItems().add(new Text("No exact matches found!"));
            vehiclesList.getItems().add(new Text("Here are some vehicles that partially match your search:"));
            partialMatches.sort((v1, v2) -> Integer.compare(getWordsMatched(v2, wordList), getWordsMatched(v1, wordList)));
            partialMatches.forEach(vehicle -> vehiclesList.getItems().add(new Text(vehicle.toString())));

        }

        sortBy(new ActionEvent());

        ifVehicleChosenSwitchScenes();

    }

    private List<Vehicle> filterByYear(List<Vehicle> vehicles) {
        String selectedYear = yearSpin.getValue();
        if (selectedYear.equals("Choose year")) return vehicles;

        return vehicles.stream()
                .filter(vehicle -> vehicle.getYear().equals(selectedYear))
                .collect(Collectors.toList());
    }

    private int getWordsMatched(Vehicle vehicle, String[] wordList) {
        int wordsMatched = 0;
        for (String word : wordList) {
            if (vehicle.toString().toLowerCase().contains(word)) {
                wordsMatched++;
            }
        }
        return wordsMatched;
    }

    private List<Vehicle> filterByColor(List<Vehicle> vehicles) {
        Color selectedColor = colorPicker.getValue();
        if (selectedColor == null) return vehicles;

        String colorAsString = convertColorToString(selectedColor);
        if (colorAsString.equals("Unknown color")) {
            return Collections.emptyList();
        }

        return vehicles.stream()
                .filter(vehicle -> vehicle.getColor().equalsIgnoreCase(colorAsString))
                .collect(Collectors.toList());
    }

    private List<Vehicle> filterByPrice(List<Vehicle> vehicles) {

        if (priceFromSlider.getValue() == PRICE_LOW && priceToSlider.getValue() == PRICE_MAX) return vehicles;


        return vehicles.stream()
                .filter(vehicle -> vehicle.getPricePerDay() >= priceFromSlider.getValue() && vehicle.getPricePerDay() <= priceToSlider.getValue()) // filter by price
                .collect(Collectors.toList());
    }

    private List<Vehicle> filterByType(List<Vehicle> vehicles) {
        if (checkBoxTypeIsNull()) return vehicles;

        List<String> selectedTypes = getSelectedTypes();
        return vehicles.stream()
                .filter(vehicle -> selectedTypes.contains(vehicle.getType().toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Vehicle> filterByPowertrain(List<Vehicle> vehicles) {
        if (checkBoxPowertrainIsNull()) return vehicles;

        List<String> selectedPowertrains = getSelectedPowertrains();
        return vehicles.stream()
                .filter(vehicle -> selectedPowertrains.contains(vehicle.getPowertrain().toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Vehicle> filterBySearchText(List<Vehicle> vehicles) {
        String[] wordList = searchField.getText().toLowerCase().split(" ");
        return vehicles.stream()
                .filter(vehicle -> Arrays.stream(wordList).allMatch(word -> vehicle.toString().toLowerCase().contains(word)))
                .collect(Collectors.toList());
    }

    private List<String> getSelectedTypes() {
        List<String> types = new ArrayList<>();
        if (camperCheck.isSelected()) types.add("camper");
        if (carCheck.isSelected()) types.add("car");
        if (motorcycleCheck.isSelected()) types.add("motorcycle");
        if (pickupCheck.isSelected()) types.add("pickup");
        return types;
    }

    private List<String> getSelectedPowertrains() {
        List<String> powertrains = new ArrayList<>();
        if (bevCheck.isSelected()) powertrains.add("bev");
        if (hybridCheck.isSelected()) powertrains.add("hybrid");
        if (iceCheck.isSelected()) powertrains.add("ice");
        return powertrains;
    }


    private boolean isSearchCriteriaEmpty() {
        return searchField.getText().isEmpty() &&
                colorPicker.getValue() == null &&
                (choiceBoxSortBy.getSelectionModel().getSelectedItem() == null || Objects.equals(choiceBoxSortBy.getValue(), "Sort by")) &&
                checkBoxTypeIsNull() &&
                checkBoxPowertrainIsNull() &&
                priceToSlider.getValue() == PRICE_MAX &&
                priceFromSlider.getValue() == PRICE_LOW &&
                (yearSpin.getValue().equals("All") || yearSpin.getValue().equals("Choose year"));
    }

    private boolean checkBoxTypeIsNull() {
        return !camperCheck.isSelected() && !carCheck.isSelected() && !motorcycleCheck.isSelected() && !pickupCheck.isSelected();
    }

    private boolean checkBoxPowertrainIsNull() {
        return !bevCheck.isSelected() && !hybridCheck.isSelected() && !iceCheck.isSelected();
    }

    private void showAll() {
        vehiclesList.getItems().clear();
        for (Vehicle vehicle : vehicles) {
            vehiclesList.getItems().add(new Text(vehicle.toString()));
        }
    }


    @FXML
    void anyColor(ActionEvent event) { // clear color picker
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

    @FXML
    void clearFilterPressed(ActionEvent event) {
        colorPicker.setValue(null);
        searchField.clear();
        camperCheck.setSelected(false);
        carCheck.setSelected(false);
        motorcycleCheck.setSelected(false);
        pickupCheck.setSelected(false);
        bevCheck.setSelected(false);
        hybridCheck.setSelected(false);
        iceCheck.setSelected(false);
        priceFromSlider.setValue(PRICE_LOW);
        priceToSlider.setValue(PRICE_MAX);
        choiceBoxSortBy.setValue("Sort by");
        yearSpin.getValueFactory().setValue("Choose year");
        showAll();

    }

    @FXML
    void exitPressed(ActionEvent event) {
        stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

}