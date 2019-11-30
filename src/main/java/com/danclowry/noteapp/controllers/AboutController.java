package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.util.AlertBuilder;
import com.danclowry.noteapp.util.LoadResource;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {
    @FXML
    private GridPane parentGrid;
    @FXML
    private Hyperlink githubLink;
    @FXML
    private Hyperlink licenceLink;
    @FXML
    private Hyperlink creditsLink;
    @FXML
    private VBox contactContainer;

    private TextArea appLicence;
    private Accordion creditsAccordion;

    public void initialize() {
        githubLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://github.com/DanClowry/MarkdownNotes"));
            } catch (IOException ex) {
                AlertBuilder.createExceptionAlert("Error - Failed to open link",
                        "Could not open link in browser.", ex);
            }
        });

        licenceLink.setOnAction(e -> {
            parentGrid.getChildren().remove(contactContainer);
            parentGrid.getChildren().remove(appLicence);
            parentGrid.getChildren().remove(creditsAccordion);
            appLicence = new TextArea(loadLicence("noteapp-licence"));
            appLicence.setEditable(false);
            parentGrid.add(appLicence, 1, 1);
        });

        creditsLink.setOnAction(e -> {
            parentGrid.getChildren().remove(contactContainer);
            parentGrid.getChildren().remove(appLicence);
            parentGrid.getChildren().remove(creditsAccordion);
            creditsAccordion = new Accordion();

            TextArea flexmarkText = new TextArea(loadLicence("flexmark-licence"));
            flexmarkText.setEditable(false);
            TitledPane flexmarkPane = new TitledPane("flexmark-java", flexmarkText);

            TextArea mysqlText = new TextArea(loadLicence("mysql-licence"));
            mysqlText.setEditable(false);
            TitledPane mysqlPane = new TitledPane("MySQL Connector/J 8.0", mysqlText);

            creditsAccordion.getPanes().addAll(flexmarkPane, mysqlPane);
            parentGrid.add(creditsAccordion, 1, 1);
        });
    }

    private String loadLicence(String licenceName) {
        String licencePath = "/misc/" + licenceName;
        String licenceText = "";
        try {
            licenceText = LoadResource.LoadResource(licencePath);
        } catch (IOException | URISyntaxException ex) {
            AlertBuilder.createExceptionAlert("Error - failed to load licence",
                    "Could not load credit licence text", ex);
        }

        return licenceText;
    }
}
