module com.danclowry.noteapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.danclowry.noteapp to javafx.fxml;
    exports com.danclowry.noteapp;
    exports com.danclowry.noteapp.controllers;
}
