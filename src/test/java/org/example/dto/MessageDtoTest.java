package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MessageDtoTest {

    @Test
    void testSettersAndGetters() {
        MessageDto messageDto = new MessageDto();

        messageDto.setId(1L);
        messageDto.setWeight(2.5);
        messageDto.setSenderId(10L);
        messageDto.setReceiverId(20L);

        assertEquals(1L, messageDto.getId(), "ID должен быть равен 1");
        assertEquals(2.5, messageDto.getWeight(), "Вес должен быть равен 2.5");
        assertEquals(10L, messageDto.getSenderId(), "ID отправителя должен быть равен 10");
        assertEquals(20L, messageDto.getReceiverId(), "ID получателя должен быть равен 20");
    }

    @Test
    void testDefaultValues() {
        MessageDto messageDto = new MessageDto();

        assertNull(messageDto.getId(), "ID должен быть null по умолчанию");
        assertNull(messageDto.getWeight(), "Вес должен быть null по умолчанию");
        assertNull(messageDto.getSenderId(), "ID отправителя должен быть null по умолчанию");
        assertNull(messageDto.getReceiverId(), "ID получателя должен быть null по умолчанию");
    }

    @Test
    void testSetId() {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(5L);
        assertEquals(5L, messageDto.getId(), "ID должен быть равен 5");
    }

    @Test
    void testSetWeight() {
        MessageDto messageDto = new MessageDto();
        messageDto.setWeight(3.0);
        assertEquals(3.0, messageDto.getWeight(), "Вес должен быть равен 3.0");
    }

    @Test
    void testSetSenderId() {
        MessageDto messageDto = new MessageDto();
        messageDto.setSenderId(15L);
        assertEquals(15L, messageDto.getSenderId(), "ID отправителя должен быть равен 15");
    }

    @Test
    void testSetReceiverId() {
        MessageDto messageDto = new MessageDto();
        messageDto.setReceiverId(25L);
        assertEquals(25L, messageDto.getReceiverId(), "ID получателя должен быть равен 25");
    }
}
