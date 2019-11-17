package com.danclowry.noteapp.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

public class MarkdownParser {

    private Parser parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();
    private String htmlTemplate = "<!DOCTYPE html><body>PLACEHOLDER</body>";

    public String parseToHTML(String input) {
        String mdHTML = renderer.render(parser.parse(input));
        return htmlTemplate.replace("PLACEHOLDER", mdHTML);
    }
}
