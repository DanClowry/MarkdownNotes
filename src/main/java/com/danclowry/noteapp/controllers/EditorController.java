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
    }
}
