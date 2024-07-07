module com.example.mygame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;



    opens com.example.mygame to javafx.fxml;
    exports com.example.mygame;
}