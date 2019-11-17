module com.danclowry.noteapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires flexmark;
    requires flexmark.util;

    opens com.danclowry.noteapp to javafx.fxml;
    exports com.danclowry.noteapp;
    exports com.danclowry.noteapp.controllers;
}
