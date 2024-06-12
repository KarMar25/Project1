module org.example.terrificproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;


    opens org.example.terrificproject to javafx.fxml;
    exports org.example.terrificproject;
}