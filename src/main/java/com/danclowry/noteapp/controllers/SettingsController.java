package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.database.DatabaseConfig;
import com.danclowry.noteapp.database.mysql.MySqlRepository;
import com.danclowry.noteapp.database.Repository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle("Error - Failed to connect to database");
                alert.setContentText("Could not connect to database. Save anyway?");
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Connected");
                alert.setContentText("Successfully connected to database.");
                alert.showAndWait();
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error - Failed to connect to database");
            alert.setContentText("Could not connect to database. Check that your credentials are correct.");
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
