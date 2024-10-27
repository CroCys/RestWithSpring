package org.example.controller;

import org.example.dto.MessageDto;
import org.example.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MessageControllerTest {
    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageDto = new MessageDto();
        messageDto.setId(1L);
        messageDto.setWeight(2.5);
        messageDto.setSenderId(1L);
        messageDto.setReceiverId(2L);
    }

    @Test
    void testGetAllMessages() {
        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        when(messageService.getAllMessages()).thenReturn(messages);

        List<MessageDto> result = messageController.getAllMessages();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(messageDto, result.get(0));
        verify(messageService, times(1)).getAllMessages();
    }

    @Test
    void testGetMessageById() {
        when(messageService.getMessageById(1L)).thenReturn(messageDto);

        ResponseEntity<MessageDto> response = messageController.getMessageById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messageDto, response.getBody());
        verify(messageService, times(1)).getMessageById(1L);
    }

    @Test
    void testCreateMessage() {
        when(messageService.createMessage(any(MessageDto.class))).thenReturn(messageDto);

        ResponseEntity<MessageDto> response = messageController.createMessage(messageDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(messageDto, response.getBody());
        verify(messageService, times(1)).createMessage(any(MessageDto.class));
    }

    @Test
    void testDeleteMessage() {
        doNothing().when(messageService).deleteMessage(1L);

        ResponseEntity<Void> response = messageController.deleteMessage(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(messageService, times(1)).deleteMessage(1L);
    }
}
