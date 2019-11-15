module com.danclowry.noteapp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.danclowry.noteapp to javafx.fxml;
    exports com.danclowry.noteapp;
}