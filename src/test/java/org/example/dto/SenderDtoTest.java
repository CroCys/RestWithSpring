package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SenderDtoTest {

    @Test
    void testSettersAndGetters() {
        SenderDto senderDto = new SenderDto();

        senderDto.setId(1L);
        senderDto.setName("John Doe");

        assertEquals(1L, senderDto.getId(), "ID должен быть равен 1");
        assertEquals("John Doe", senderDto.getName(), "Имя должно быть 'John Doe'");
    }

    @Test
    void testDefaultValues() {
        SenderDto senderDto = new SenderDto();

        assertNull(senderDto.getId(), "ID должен быть null по умолчанию");
        assertNull(senderDto.getName(), "Имя должно быть null по умолчанию");
    }

    @Test
    void testSetId() {
        SenderDto senderDto = new SenderDto();
        senderDto.setId(10L);
        assertEquals(10L, senderDto.getId(), "ID должен быть равен 10");
    }

    @Test
    void testSetName() {
        SenderDto senderDto = new SenderDto();
        senderDto.setName("Alice");
        assertEquals("Alice", senderDto.getName(), "Имя должно быть 'Alice'");
    }
}
