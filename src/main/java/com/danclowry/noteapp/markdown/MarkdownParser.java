package com.danclowry.noteapp.markdown;

public class MarkdownParser {

    private String htmlTemplate = "<!DOCTYPE html><body><p>PLACEHOLDER</p></body>";

    public String parseToHTML(String input) {
        return htmlTemplate.replace("PLACEHOLDER", input);
    }
}
