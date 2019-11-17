package com.danclowry.noteapp.markdown;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownParserTest {

    @Test
    public void parseToHTML_inputNormalText_returnsParagraphInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p>body text</p>\n</body>",
                mdParser.parseToHTML("body text"),
                "Input text should be placed within a paragraph <p> element");
    }

    @Test
    public void parseToHTML_inputLevelOneHeading_returnsH1InHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h1>level one heading</h1>\n</body>",
                mdParser.parseToHTML("# level one heading"),
                "Input text should be placed within a level-one heading <h1> element");
    }

    @Test
    public void parseToHTML_inputHeadingAndBody_returnsH1AndParagraphElements() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h1>level one heading</h1>\n<p>body text</p>\n</body>",
                mdParser.parseToHTML("# level one heading\nbody text"),
                "First line of input should be placed within a level-one heading <h1> element. " +
                    "Second line of input should be placed within a separate paragraph <p> element.");
    }
}
