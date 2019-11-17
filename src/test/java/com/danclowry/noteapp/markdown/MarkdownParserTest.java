package com.danclowry.noteapp.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownParserTest {

    @Test
    public void parseToHTML_inputNormalText_returnsParagraphInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p>body text</p></body>",
                mdParser.parseToHTML("body text"),
                "Input text should be placed within a paragraph <p> element");
    }
}
