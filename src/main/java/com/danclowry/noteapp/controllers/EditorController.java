package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.database.mysql.MySqlRepository;
import com.danclowry.noteapp.markdown.MarkdownParser;
import com.danclowry.noteapp.models.Note;
import com.danclowry.noteapp.util.AlertBuilder;
import com.danclowry.noteapp.util.LoadFXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditorController {
    @FXML
    private TextArea markdownEditor;
    @FXML
    private WebView markdownViewer;
    @FXML
    private MenuItem settingsMenuItem;
    @FXML
    private ListView notesListView;

    public void initialize() {
        MarkdownParser mdParser = new MarkdownParser();

        markdownViewer.getEngine().setUserStyleSheetLocation(getClass().getResource("/css/md-viewer.css").toString());

        markdownEditor.textProperty().addListener((observable, oldValue, newValue) ->
                markdownViewer.getEngine().loadContent(mdParser.parseToHTML(markdownEditor.getText()), "text/html"));

        // Replace tab characters with four spaces
        markdownEditor.setOnKeyTyped(e -> {
            if (e.getCharacter().equals("\t")) {
                int caret = markdownEditor.getCaretPosition();
                markdownEditor.setText(markdownEditor.getText().replaceAll("\\t", "    "));
                // Position the caret four characters in front of its original position.
                // Called because textSet resets the caret to the beginning of the text.
                markdownEditor.positionCaret(caret + 3);
            }
            e.consume();
        });

        // Open settings window
        settingsMenuItem.setOnAction(e -> {
            try {
                Stage settings = new Stage();
                settings.setScene(new Scene(LoadFXML.loadFXML("settings")));
                settings.initModality(Modality.APPLICATION_MODAL);
                settings.setResizable(false);
                settings.showAndWait();
            } catch (IOException ex) {
                Alert alert = AlertBuilder.createAlert(null, "Error opening settings menu", Alert.AlertType.ERROR);
                alert.showAndWait();
            }
        });

        // Set cells to show title of note and wrap text
        notesListView.setCellFactory(p -> new ListCell<Note>() {
            @Override
            protected void updateItem(Note note, boolean empty) {
                super.updateItem(note, empty);

                if (empty || note == null || note.getTitle() == null) {
                    setText(null);
                } else {
                    setText(note.getTitle());
                    setWrapText(true);
                    setPrefWidth(notesListView.getWidth() - 2.0);
                }
            }
        });

        try {
            Repository repository = new MySqlRepository();
            ObservableList<Note> notesList = FXCollections.observableList(repository.getListOfNotes());
            notesListView.setItems(notesList);
        } catch (SQLException ex) {
            Alert alert = AlertBuilder.createExceptionAlert("Error- Failed to load saved notes",
                    "Could not load saved notes from the database.\n" +
                            "Please check your database connection settings", ex);
            alert.showAndWait();
        }
    }
}
