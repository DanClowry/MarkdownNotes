package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.database.Repository;
import com.danclowry.noteapp.database.mysql.MySqlRepository;
import com.danclowry.noteapp.markdown.MarkdownParser;
import com.danclowry.noteapp.models.Note;
import com.danclowry.noteapp.util.AlertBuilder;
import com.danclowry.noteapp.util.LoadFXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private TextField titleField;
    @FXML
    private MenuItem settingsMenuItem;
    @FXML
    private ListView notesListView;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addNoteButton;

    // TODO: Make observable
    private Note currentNote;
    private int currentIndex;

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

                if (empty || note == null) {
                    setText(null);
                } else {
                    setText(note.getTitle());
                    setWrapText(true);
                    setPrefWidth(notesListView.getWidth() - 2.0);
                }
            }
        });

        getAllNotes();

        notesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Note>() {
            @Override
            public void changed(ObservableValue<? extends Note> observableValue, Note oldNote, Note newNote) {
                if (oldNote != null && oldNote.getTitle() != null) {
                    SaveNote(oldNote);
                }
                if (notesListView.getItems().size() == 0) {
                    newNote = new Note(null, null);
                }
                if (newNote != null) {
                    currentNote = newNote;
                }

                currentIndex = notesListView.getSelectionModel().getSelectedIndex();
                if (currentNote.getContent() != null) {
                    markdownEditor.setText(currentNote.getContent());
                } else {
                    markdownEditor.setText("");
                }
                if (currentNote.getTitle() != null) {
                    titleField.setText(currentNote.getTitle());
                } else {
                    titleField.setText("");
                }

            }
        });

        markdownEditor.textProperty().addListener((observableValue, oldString, newString) -> currentNote.setContent(newString));
        titleField.textProperty().addListener((observableValue, oldString, newString) -> {
            currentNote.setTitle(newString);
            if (currentIndex == -1 && notesListView.getItems().size() == 1) {
                currentIndex = 0;
            }
            if (currentIndex != -1) {
                notesListView.getItems().set(currentIndex, currentNote);
            }
        });

        saveButton.setOnAction(e -> SaveNote(currentNote));

        deleteButton.setOnAction(e -> {
            try {
                Repository repository = new MySqlRepository();
                repository.deleteNote(currentNote);
            } catch (SQLException ex) {
                Alert alert = AlertBuilder.createExceptionAlert("Error- Failed to delete note",
                        "Could not delete note from database.\n" +
                                "Please check your database connection settings", ex);
                alert.showAndWait();
            }

            getAllNotes();
            notesListView.refresh();
        });

        addNoteButton.setOnAction(e -> {
            if (currentNote != null && currentNote.getTitle() != null) {
                SaveNote(currentNote);
            }
            currentNote = new Note(null, null);
            currentIndex = notesListView.getItems().size();
            notesListView.getItems().add(currentNote);
            notesListView.getSelectionModel().select(currentIndex);
            markdownEditor.setText("");
            titleField.setText("");
        });
    }

    private void SaveNote(Note note) {
        try {
            if (note.getTitle().length() >= 100) {
                Alert alert = AlertBuilder.createAlert("Title too long",
                        "Note title must be below 100 characters.\n" +
                                "Please shorten your title.", Alert.AlertType.WARNING);
                alert.showAndWait();
                return;
            }

            Repository repository = new MySqlRepository();
            if (note.getId() == 0) {
                note.setId(repository.createNote(note));
            } else {
                repository.updateNote(note);
            }
        } catch (SQLException ex) {
            Alert alert = AlertBuilder.createExceptionAlert("Error- Failed to save note",
                    "Could not save note to database.\n" +
                            "Please check your database connection settings", ex);
            alert.showAndWait();
        }
    }

    private void getAllNotes() {
        try {
            Repository repository = new MySqlRepository();
            ObservableList<Note> notesList = FXCollections.observableList(repository.getListOfNotes());
            notesListView.setItems(notesList);
            if (notesList.size() == 0) {
                currentNote = new Note(null, null);
                notesListView.getItems().add(currentNote);
                currentIndex = 0;
            } else {
                currentNote = (Note) notesListView.getItems().get(0);
                currentIndex = 0;
            }
        } catch (SQLException ex) {
            Alert alert = AlertBuilder.createExceptionAlert("Error- Failed to load saved notes",
                    "Could not load saved notes from the database.\n" +
                            "Please check your database connection settings", ex);
            alert.showAndWait();
        }
    }
}
