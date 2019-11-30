package com.danclowry.noteapp.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class MarkdownParser {

    private final MutableDataHolder options = new MutableDataSet();
    private Parser parser;
    private HtmlRenderer renderer;
    private String htmlTemplate = "<!DOCTYPE html><body>PLACEHOLDER</body>";

    public MarkdownParser() {
        this(new MutableDataSet());
    }

    public MarkdownParser(DataHolder parserOptions) {
        options.setAll(parserOptions);
        parser =  Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public String parseToHTML(String input) {
        String mdHTML = renderer.render(parser.parse(input));
        return htmlTemplate.replace("PLACEHOLDER", mdHTML);
    }
}
