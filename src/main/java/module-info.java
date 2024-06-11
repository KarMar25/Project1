module org.example.terrificproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;


    opens org.example.terrificproject to javafx.fxml, com.google.gson;
    exports org.example.terrificproject;
}