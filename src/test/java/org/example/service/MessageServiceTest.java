package org.example.service;

import org.example.dto.MessageDto;
import org.example.entity.Message;
import org.example.mapper.MessageMapper;
import org.example.repository.MessageRepository;
import org.example.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MessageServiceTest {
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message message;
    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        message = new Message();
        message.setId(1L);
        message.setWeight(2.5);

        messageDto = new MessageDto();
        messageDto.setId(1L);
        messageDto.setWeight(2.5);
        messageDto.setSenderId(1L);
        messageDto.setReceiverId(2L);
    }

    @Test
    void testGetAllMessages() {
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        when(messageRepository.findAll()).thenReturn(messages);
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        List<MessageDto> result = messageService.getAllMessages();

        assertEquals(1, result.size());
        assertEquals(messageDto, result.get(0));
        verify(messageRepository, times(1)).findAll();
    }

    @Test
    void testGetMessageById() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.getMessageById(1L);

        assertNotNull(result);
        assertEquals(messageDto, result);
        verify(messageRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateMessage() {
        when(messageMapper.toEntity(messageDto)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.createMessage(messageDto);

        assertNotNull(result);
        assertEquals(messageDto, result);
        verify(messageMapper, times(1)).toEntity(messageDto);
        verify(messageRepository, times(1)).save(message);
    }

    @Test
    void testDeleteMessage() {
        when(messageRepository.existsById(1L)).thenReturn(true);

        messageService.deleteMessage(1L);

        verify(messageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMessageNotFound() {
        when(messageRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            messageService.deleteMessage(1L);
        });

        assertEquals("Message not found with id: 1", exception.getMessage());
        verify(messageRepository, never()).deleteById(any());
    }
}
