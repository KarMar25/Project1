module org.example.terrificproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.terrificproject to javafx.fxml;
    exports org.example.terrificproject;
}