package org.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReceiverDtoTest {

    @Test
    void testSettersAndGetters() {
        ReceiverDto receiverDto = new ReceiverDto();

        receiverDto.setId(1L);
        receiverDto.setName("John Smith");

        assertEquals(1L, receiverDto.getId(), "ID должен быть равен 1");
        assertEquals("John Smith", receiverDto.getName(), "Имя должно быть 'John Smith'");
    }

    @Test
    void testDefaultValues() {
        ReceiverDto receiverDto = new ReceiverDto();

        assertNull(receiverDto.getId(), "ID должен быть null по умолчанию");
        assertNull(receiverDto.getName(), "Имя должно быть null по умолчанию");
    }

    @Test
    void testSetId() {
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setId(5L);
        assertEquals(5L, receiverDto.getId(), "ID должен быть равен 5");
    }

    @Test
    void testSetName() {
        ReceiverDto receiverDto = new ReceiverDto();
        receiverDto.setName("Alice");
        assertEquals("Alice", receiverDto.getName(), "Имя должно быть 'Alice'");
    }
}
