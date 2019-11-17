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

    @Test
    public void parseToHTML_inputLevelOneHeading_returnsH1InHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h1>level one heading</h1></body>",
                mdParser.parseToHTML("# level one heading"),
                "Input text should be placed within a level-one heading <h1> element");
    }
}
