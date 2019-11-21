package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.database.MySql.MySqlRepository;
import com.danclowry.noteapp.database.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SettingsController {
    @FXML
    private TextField hostnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button testButton;

    public void initialize() {
        testButton.setOnAction(e -> {
            try {
                Repository repository = new MySqlRepository(hostnameField.getText(),
                        usernameField.getText(), passwordField.getText());
                
                if (repository.testConnection()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Connected");
                    alert.setContentText("Successfully connected to database.");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error - Failed to connect to database");
                alert.setContentText("Could not connect to database. Check that your credentials are correct.");
                alert.showAndWait();
            }
        });
    }
}
