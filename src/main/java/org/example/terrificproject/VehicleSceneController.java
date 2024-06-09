package org.example.terrificproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class VehicleSceneController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ListView<?> propertiesList;

    @FXML
    private ImageView vehicleImage;

    @FXML
    private Text vehicleName;

    @FXML
    void reservePressed(ActionEvent event) {

    }

}

