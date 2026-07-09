package org.telegram.telegrambots.meta.api.objects.richtext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestRichText {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testRichTextBoldTypeConstant() {
        assertEquals("bold", RichTextBold.TYPE);
    }

    @Test
    public void testRichTextAnchorTypeConstant() {
        assertEquals("anchor", RichTextAnchor.TYPE);
    }

    @Test
    public void testRichTextCustomEmojiTypeConstant() {
        assertEquals("custom_emoji", RichTextCustomEmoji.TYPE);
    }

    @Test
    public void testRichTextBoldBuilder() {
        RichTextAnchor inner = RichTextAnchor.builder().name("section").build();
        RichTextBold bold = RichTextBold.builder().text(inner).build();

        assertEquals("bold", bold.getType());
        assertEquals(inner, bold.getText());
    }

    @Test
    public void testRichTextAnchorBuilder() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        assertEquals("anchor", anchor.getType());
        assertEquals("chapter-1", anchor.getName());
    }

    @Test
    public void testRichTextCustomEmojiBuilder() {
        RichTextCustomEmoji emoji = RichTextCustomEmoji.builder()
                .customEmojiId("5368324170671202286")
                .alternativeText("👍")
                .build();

        assertEquals("custom_emoji", emoji.getType());
        assertEquals("5368324170671202286", emoji.getCustomEmojiId());
        assertEquals("👍", emoji.getAlternativeText());
    }

    @Test
    public void testRichTextAnchorRoundTrip() throws IOException {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        String json = mapper.writeValueAsString(anchor);
        assertEquals(anchor, mapper.readValue(json, RichTextAnchor.class));
    }

    @Test
    public void testRichTextPolymorphicDeserializationBold() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("section").build();
        RichTextBold bold = RichTextBold.builder().text(inner).build();

        String json = mapper.writeValueAsString(bold);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextBold.class, deserialized);
        assertEquals(bold, deserialized);
    }

    @Test
    public void testRichTextPolymorphicDeserializationAnchor() throws IOException {
        RichTextAnchor anchor = RichTextAnchor.builder().name("chapter-1").build();

        String json = mapper.writeValueAsString(anchor);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextAnchor.class, deserialized);
        assertEquals(anchor, deserialized);
    }

    @Test
    public void testRichTextPolymorphicDeserializationCustomEmoji() throws IOException {
        RichTextCustomEmoji emoji = RichTextCustomEmoji.builder()
                .customEmojiId("5368324170671202286")
                .alternativeText("👍")
                .build();

        String json = mapper.writeValueAsString(emoji);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextCustomEmoji.class, deserialized);
        assertEquals(emoji, deserialized);
    }

    @Test
    public void testRichTextItalicRoundTrip() throws IOException {
        RichTextAnchor inner = RichTextAnchor.builder().name("test").build();
        RichTextItalic italic = RichTextItalic.builder().text(inner).build();

        String json = mapper.writeValueAsString(italic);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextItalic.class, deserialized);
        assertEquals(italic, deserialized);
    }

    @Test
    public void testRichTextMathematicalExpressionRoundTrip() throws IOException {
        RichTextMathematicalExpression math = RichTextMathematicalExpression.builder()
                .expression("E = mc^2")
                .build();

        String json = mapper.writeValueAsString(math);
        RichText deserialized = mapper.readValue(json, RichText.class);

        assertInstanceOf(RichTextMathematicalExpression.class, deserialized);
        assertEquals(math, deserialized);
    }
}
