module com.danclowry.noteapp {
    requires java.prefs;
    requires java.naming;
    requires java.sql;
    requires mysql.connector.java;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires flexmark;
    requires flexmark.util;

    opens com.danclowry.noteapp to javafx.fxml;
    opens com.danclowry.noteapp.controllers to javafx.fxml;
    exports com.danclowry.noteapp;
    exports com.danclowry.noteapp.controllers;
}
