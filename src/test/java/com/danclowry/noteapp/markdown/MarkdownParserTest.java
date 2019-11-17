package com.danclowry.noteapp.markdown;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarkdownParserTest {

    @Test
    public void parseToHTML_inputNormalText_returnsParagraphInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p>Body text</p>\n</body>",
                mdParser.parseToHTML("Body text"),
                "Input text should be placed within a paragraph <p> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"*Emphasised text*", "_Emphasised text_"})
    public void parseToHTML_inputSingleAsteriskOrUnderscore_returnsEmphasisInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p><em>Emphasised text</em></p>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within an emphasis <em> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"**Bolded text**", "__Bolded text__"})
    public void parseToHTML_inputDoubleAsteriskOrUnderscore_returnsStrongInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p><strong>Bolded text</strong></p>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within a strong <strong> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"# Level One Heading", "Level One Heading\n=", "#         Level One Heading"})
    public void parseToHTML_inputSingleHash_returnsH1InHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h1>Level One Heading</h1>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within a level-one heading <h1> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"## Level Two Heading", "Level Two Heading\n-", "##         Level Two Heading"})
    public void parseToHTML_inputDoubleHash_returnsH2InHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h2>Level Two Heading</h2>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within a level-two heading <h2> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[Hyperlink](https://example.com/)", "[Hyperlink][1]\n\n[1]: https://example.com/"})
    public void parseToHTML_inputHyperlink_returnsAnchorHrefInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p><a href=\"https://example.com/\">Hyperlink</a></p>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should link to example.com using an anchor <a> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"![MD Logo](https://upload.wikimedia.org/wikipedia/commons/4/48/Markdown-mark.svg)",
            "![MD Logo][1]\n\n[1]: https://upload.wikimedia.org/wikipedia/commons/4/48/Markdown-mark.svg"})
    public void parseToHTML_inputImageLink_returnsImgInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p><img src=\"https://upload.wikimedia.org/wikipedia/commons/4/48/Markdown-mark.svg\" alt=\"MD Logo\" /></p>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should create an image <img> element that contains a src and alt attribute");
    }

    @Test
    public void parseToHTML_inputGreaterThan_returnsBlockquoteInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><blockquote>\n<p>Quoted text</p>\n</blockquote>\n</body>",
                mdParser.parseToHTML("> Quoted text"),
                "Input text should be placed within a blockquote <blockquote> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"* UL item one\n* UL item two\n* UL item three",
            "- UL item one\n- UL item two\n- UL item three"})
    public void parseToHTML_inputAsteriskOrHyphenList_returnsUnorderedListInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><ul>\n<li>UL item one</li>\n<li>UL item two</li>\n<li>UL item three</li>\n</ul>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within list-item <li> elements contained within an unordered list <ul> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1. OL item one\n2. OL item two\n3. OL item three",
            "1) OL item one\n2) OL item two\n3) OL item three",
            "1. OL item one\n9. OL item two\n4. OL item three"})
    public void parseToHTML_inputNumberedList_returnsOrderedListInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><ol>\n<li>OL item one</li>\n<li>OL item two</li>\n<li>OL item three</li>\n</ol>\n</body>",
                mdParser.parseToHTML(input),
                "Input text should be placed within list-item <li> elements contained within an ordered list <ol> element");
    }

    @ParameterizedTest
    @ValueSource(strings = {"***", "---"})
    public void parseToHTML_inputTripleAsteriskOrHyphen_returnsHorizontalRuleInHTMLBody(String input) {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><hr />\n</body>",
                mdParser.parseToHTML(input),
                "Input text should create a horizontal rule <hr /> element");
    }

    @Test
    public void parseToHTML_inputBacktick_returnsInlineCodeInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><p>Run <code>mvn clean test</code> to test the program</p>\n</body>",
                mdParser.parseToHTML("Run `mvn clean test` to test the program"),
                "Input text surrounded by single backticks should be placed within a code <code> element");
    }

    @Test
    public void parseToHTML_inputTripleBacktick_returnsCodeBlockInHTMLBody() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><pre><code>class CodeBlocks \n" +
                        "{ \n" +
                        "    public static void main(String args[]) \n" +
                        "    { \n" +
                        "        System.out.println(&quot;Surround lines in three backticks to create a code block&quot;); \n" +
                        "    } \n" +
                        "}\n</code></pre>\n</body>",
                mdParser.parseToHTML("```\n" +
                        "class CodeBlocks \n" +
                        "{ \n" +
                        "    public static void main(String args[]) \n" +
                        "    { \n" +
                        "        System.out.println(\"Surround lines in three backticks to create a code block\"); \n" +
                        "    } \n" +
                        "}\n" +
                        "```"),
                "Input text surrounded by single backticks should be placed within a code <code> element");
    }

    @Test
    public void parseToHTML_inputHeadingAndBody_returnsH1AndParagraphElements() {
        MarkdownParser mdParser = new MarkdownParser();
        assertEquals("<!DOCTYPE html><body><h1>Level One Heading</h1>\n<p>Body text</p>\n</body>",
                mdParser.parseToHTML("# Level One Heading\nBody text"),
                "First line of input should be placed within a level-one heading <h1> element. " +
                    "Second line of input should be placed within a separate paragraph <p> element");
    }
}
