package org.telegram.telegrambots.meta.api.objects.richblock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextAnchor;
import org.telegram.telegrambots.meta.api.objects.richtext.RichTextMathematicalExpression;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestRichBlock {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void testRichBlockParagraphTypeConstant() {
        assertEquals("paragraph", RichBlockParagraph.TYPE);
    }

    @Test
    public void testRichBlockDividerTypeConstant() {
        assertEquals("divider", RichBlockDivider.TYPE);
    }

    @Test
    public void testRichBlockSectionHeadingTypeConstant() {
        assertEquals("heading", RichBlockSectionHeading.TYPE);
    }

    @Test
    public void testRichBlockListTypeConstant() {
        assertEquals("list", RichBlockList.TYPE);
    }

    @Test
    public void testRichBlockParagraphBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("section").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();

        assertEquals("paragraph", paragraph.getType());
        assertEquals(text, paragraph.getText());
    }

    @Test
    public void testRichBlockDividerBuilder() {
        RichBlockDivider divider = RichBlockDivider.builder().build();

        assertEquals("divider", divider.getType());
    }

    @Test
    public void testRichBlockSectionHeadingBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("heading").build();
        RichBlockSectionHeading heading = RichBlockSectionHeading.builder()
                .text(text)
                .size(1)
                .build();

        assertEquals("heading", heading.getType());
        assertEquals(text, heading.getText());
        assertEquals(1, heading.getSize());
    }

    @Test
    public void testRichBlockMathematicalExpressionBuilder() {
        RichBlockMathematicalExpression math = RichBlockMathematicalExpression.builder()
                .expression("E = mc^2")
                .build();

        assertEquals("mathematical_expression", math.getType());
        assertEquals("E = mc^2", math.getExpression());
    }

    @Test
    public void testRichBlockListBuilder() {
        RichTextAnchor text = RichTextAnchor.builder().name("item").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();
        RichBlockListItem item = RichBlockListItem.builder()
                .label("1.")
                .blocks(List.of(paragraph))
                .build();
        RichBlockList list = RichBlockList.builder()
                .items(List.of(item))
                .build();

        assertEquals("list", list.getType());
        assertEquals(1, list.getItems().size());
        assertEquals(item, list.getItems().get(0));
    }

    @Test
    public void testRichBlockListItemOptionalFields() {
        RichTextAnchor text = RichTextAnchor.builder().name("item").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();
        RichBlockListItem item = RichBlockListItem.builder()
                .label("item1")
                .blocks(List.of(paragraph))
                .build();

        assertNull(item.getHasCheckbox());
        assertNull(item.getIsChecked());
        assertNull(item.getValue());
        assertNull(item.getType());
    }

    @Test
    public void testRichBlockPolymorphicDeserializationParagraph() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("section").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(text).build();

        String json = mapper.writeValueAsString(paragraph);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockParagraph.class, deserialized);
        assertEquals(paragraph, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationDivider() throws IOException {
        RichBlockDivider divider = RichBlockDivider.builder().build();

        String json = mapper.writeValueAsString(divider);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockDivider.class, deserialized);
        assertEquals(divider, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationSectionHeading() throws IOException {
        RichTextAnchor text = RichTextAnchor.builder().name("test").build();
        RichBlockSectionHeading heading = RichBlockSectionHeading.builder()
                .text(text)
                .size(2)
                .build();

        String json = mapper.writeValueAsString(heading);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockSectionHeading.class, deserialized);
        assertEquals(heading, deserialized);
    }

    @Test
    public void testRichBlockPolymorphicDeserializationMathematicalExpression() throws IOException {
        RichBlockMathematicalExpression math = RichBlockMathematicalExpression.builder()
                .expression("x^2 + y^2")
                .build();

        String json = mapper.writeValueAsString(math);
        RichBlock deserialized = mapper.readValue(json, RichBlock.class);

        assertInstanceOf(RichBlockMathematicalExpression.class, deserialized);
        assertEquals(math, deserialized);
    }

    @Test
    public void testRichBlockCaption() {
        RichTextAnchor text = RichTextAnchor.builder().name("caption").build();
        RichTextMathematicalExpression credit = RichTextMathematicalExpression.builder()
                .expression("Author")
                .build();
        RichBlockCaption caption = RichBlockCaption.builder()
                .text(text)
                .credit(credit)
                .build();

        assertEquals(text, caption.getText());
        assertEquals(credit, caption.getCredit());
    }

    @Test
    public void testRichBlockCaptionOptionalCredit() {
        RichTextAnchor text = RichTextAnchor.builder().name("caption").build();
        RichBlockCaption caption = RichBlockCaption.builder()
                .text(text)
                .build();

        assertEquals(text, caption.getText());
        assertNull(caption.getCredit());
    }
}
