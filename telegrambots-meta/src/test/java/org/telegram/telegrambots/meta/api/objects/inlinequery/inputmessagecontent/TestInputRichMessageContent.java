package org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.richtext.InputRichMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ruben Bermudez
 * @version 10.1
 */
public class TestInputRichMessageContent {

    @Test
    public void testInputRichMessageContentValidation() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .html("<p>Hello</p>")
                .build();
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(richMessage)
                .build();

        assertDoesNotThrow(content::validate);
    }

    @Test
    public void testInputRichMessageContentNullRichMessageThrowsNPE() {
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(InputRichMessage.builder().html("<p>Hello</p>").build())
                .build();

        assertThrows(NullPointerException.class, () -> content.setRichMessage(null));
    }

    @Test
    public void testInputRichMessageContentRichMessageField() {
        InputRichMessage richMessage = InputRichMessage.builder()
                .markdown("**Hello**")
                .build();
        InputRichMessageContent content = InputRichMessageContent.builder()
                .richMessage(richMessage)
                .build();

        assertEquals(richMessage, content.getRichMessage());
    }
}
