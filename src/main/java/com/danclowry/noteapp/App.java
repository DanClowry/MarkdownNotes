package com.danclowry.noteapp;

import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.database.mysql.MySqlRepository;
import com.danclowry.noteapp.util.AlertBuilder;
import com.danclowry.noteapp.util.LoadFXML;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Repository repository = new MySqlRepository();
            repository.setupDatabase();
        } catch (SQLException ex) {
            Alert alert = AlertBuilder.createExceptionAlert("Error - Failed to connect to database",
                    "Could not connect to database. Program will close.", ex);
            alert.showAndWait();
            Platform.exit();
        } catch (IOException | URISyntaxException ex) {
            Alert alert = AlertBuilder.createExceptionAlert("Error - Failed to connect to database",
                    "Could not load setup script. Program will close.", ex);
            alert.showAndWait();
            Platform.exit();
        }

        scene = new Scene(LoadFXML.loadFXML("editor"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
