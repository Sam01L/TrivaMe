module com.example.trivame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trivame to javafx.fxml;
    exports com.example.trivame;
}