package org.telegram.telegrambots.meta.api.objects.richtext;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlock;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlockDivider;
import org.telegram.telegrambots.meta.api.objects.richblock.RichBlockParagraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestRichMessage {

    @Test
    public void testRichMessageWithBlocks() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("section1").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();
        List<RichBlock> blocks = List.of(paragraph);

        RichMessage message = RichMessage.builder()
                .blocks(blocks)
                .build();

        assertEquals(1, message.getBlocks().size());
        assertEquals(paragraph, message.getBlocks().get(0));
    }

    @Test
    public void testRichMessageWithMultipleBlocks() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();
        RichBlockDivider divider = RichBlockDivider.builder().build();
        List<RichBlock> blocks = List.of(paragraph, divider);

        RichMessage message = RichMessage.builder()
                .blocks(blocks)
                .build();

        assertEquals(2, message.getBlocks().size());
    }

    @Test
    public void testRichMessageWithIsRtl() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();

        RichMessage message = RichMessage.builder()
                .blocks(List.of(paragraph))
                .isRtl(true)
                .build();

        assertTrue(message.getIsRtl());
    }

    @Test
    public void testRichMessageIsRtlNullByDefault() {
        RichTextAnchor anchor = RichTextAnchor.builder().name("test").build();
        RichBlockParagraph paragraph = RichBlockParagraph.builder().text(anchor).build();

        RichMessage message = RichMessage.builder()
                .blocks(List.of(paragraph))
                .build();

        assertNull(message.getIsRtl());
    }
}
