package org.example.service;

import org.example.dto.SenderDto;
import org.example.entity.Sender;
import org.example.mapper.SenderMapper;
import org.example.repository.SenderRepository;
import org.example.service.impl.SenderServiceImpl;
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

public class SenderServiceTest {
    @Mock
    private SenderRepository senderRepository;

    @Mock
    private SenderMapper senderMapper;

    @InjectMocks
    private SenderServiceImpl senderService;

    private Sender sender;
    private SenderDto senderDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sender = new Sender();
        sender.setId(1L);
        sender.setName("Alice Smith");

        senderDto = new SenderDto();
        senderDto.setId(1L);
        senderDto.setName("Alice Smith");
    }

    @Test
    void testGetAllSenders() {
        List<Sender> senders = new ArrayList<>();
        senders.add(sender);

        when(senderRepository.findAll()).thenReturn(senders);
        when(senderMapper.toDto(sender)).thenReturn(senderDto);

        List<SenderDto> result = senderService.getAllSenders();

        assertEquals(1, result.size());
        assertEquals(senderDto, result.get(0));
        verify(senderRepository, times(1)).findAll();
    }

    @Test
    void testGetSenderById() {
        when(senderRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(senderMapper.toDto(sender)).thenReturn(senderDto);

        SenderDto result = senderService.getSenderById(1L);

        assertNotNull(result);
        assertEquals(senderDto, result);
        verify(senderRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSender() {
        when(senderMapper.toEntity(senderDto)).thenReturn(sender);
        when(senderRepository.save(sender)).thenReturn(sender);
        when(senderMapper.toDto(sender)).thenReturn(senderDto);

        SenderDto result = senderService.createSender(senderDto);

        assertNotNull(result);
        assertEquals(senderDto, result);
        verify(senderMapper, times(1)).toEntity(senderDto);
        verify(senderRepository, times(1)).save(sender);
    }

    @Test
    void testDeleteSender() {
        when(senderRepository.existsById(1L)).thenReturn(true);

        senderService.deleteSender(1L);

        verify(senderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSenderNotFound() {
        when(senderRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            senderService.deleteSender(1L);
        });

        assertEquals("Sender not found with id: 1", exception.getMessage());
        verify(senderRepository, never()).deleteById(any());
    }
}
