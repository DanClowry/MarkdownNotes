package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.database.DatabaseConfig;
import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.database.mysql.MySqlRepository;
import com.danclowry.noteapp.util.AlertBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Optional;

public class SettingsController {
    @FXML
    private TextField hostnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button saveButton;
    @FXML
    private Button testButton;

    public void initialize() {
        saveButton.setOnAction(e -> {
            if (testConnection()) {
                DatabaseConfig.setHostname(hostnameField.getText());
                DatabaseConfig.setUsername(usernameField.getText());
                DatabaseConfig.setPassword(passwordField.getText());
            } else {
                Alert alert = AlertBuilder.createAlert("Error - Failed to connect to database",
                        "Could not connect to database. Save anyway?", Alert.AlertType.WARNING);
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                Optional<ButtonType> response = alert.showAndWait();
                if (response.get() == ButtonType.YES) {
                    DatabaseConfig.setHostname(hostnameField.getText());
                    DatabaseConfig.setUsername(usernameField.getText());
                    DatabaseConfig.setPassword(passwordField.getText());
                }
            }
        });

        testButton.setOnAction(e -> {
            if (testConnection()) {
                Alert alert = AlertBuilder.createAlert("Connected",
                        "Successfully connected to database.", Alert.AlertType.INFORMATION);
                alert.showAndWait();
            }
            Alert alert = AlertBuilder.createAlert("Error - Failed to connect to database",
                    "Could not connect to database. Check that your credentials are correct.", Alert.AlertType.ERROR);
            alert.showAndWait();
        });
    }

    private boolean testConnection() {
        try {
            Repository repository = new MySqlRepository(hostnameField.getText(),
                    usernameField.getText(), passwordField.getText());

            return repository.testConnection();
        } catch (SQLException ex) {
            return false;
        }
    }
}
