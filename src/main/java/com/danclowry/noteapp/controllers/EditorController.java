package com.danclowry.noteapp.controllers;

import com.danclowry.noteapp.markdown.MarkdownParser;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

public class EditorController {
    @FXML
    private TextArea markdownEditor;
    @FXML
    private WebView markdownViewer;

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
    }
}
