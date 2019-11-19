package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.markdown.MarkdownParser;
import com.danclowry.noteapp.util.LoadFXML;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditorController {
    @FXML
    private TextArea markdownEditor;
    @FXML
    private WebView markdownViewer;
    @FXML
    private MenuItem settingsMenuItem;

    public void initialize() {
        MarkdownParser mdParser = new MarkdownParser();

        markdownViewer.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/md-viewer.css").toString());

        markdownEditor.textProperty().addListener((observable, oldValue, newValue) ->
                markdownViewer.getEngine().loadContent(mdParser.parseToHTML(markdownEditor.getText()), "text/html"));

        settingsMenuItem.setOnAction(e -> {
            try {
                Stage settings = new Stage();
                settings.setScene(new Scene(LoadFXML.loadFXML("settings")));
                settings.initModality(Modality.APPLICATION_MODAL);
                settings.setResizable(false);
                settings.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error opening settings menu");
                alert.showAndWait();
            }
        });
    }
}
