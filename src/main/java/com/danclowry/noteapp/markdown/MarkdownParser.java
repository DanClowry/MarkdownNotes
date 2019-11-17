package com.danclowry.noteapp.markdown;

public class MarkdownParser {

    private String htmlTemplate = "<!DOCTYPE html><body>PLACEHOLDER</body>";

    public String parseToHTML(String input) {
        String htmlBody;

        if (input.startsWith("#")) {
            htmlBody = "<h1>" + input.substring(2) + "</h1>";
            return htmlTemplate.replace("PLACEHOLDER", htmlBody);
        }

        htmlBody = "<p>" + input + "</p>";
        return htmlTemplate.replace("PLACEHOLDER", htmlBody);
    }
}
