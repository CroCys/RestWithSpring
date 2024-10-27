package org.example.service;

import org.example.dto.ReceiverDto;
import org.example.entity.Receiver;
import org.example.mapper.ReceiverMapper;
import org.example.repository.ReceiverRepository;
import org.example.service.impl.ReceiverServiceImpl;
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

public class ReceiverServiceTest {
    @Mock
    private ReceiverRepository receiverRepository;

    @Mock
    private ReceiverMapper receiverMapper;

    @InjectMocks
    private ReceiverServiceImpl receiverService;

    private Receiver receiver;
    private ReceiverDto receiverDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        receiver = new Receiver();
        receiver.setId(1L);
        receiver.setName("John Doe");

        receiverDto = new ReceiverDto();
        receiverDto.setId(1L);
        receiverDto.setName("John Doe");
    }

    @Test
    void testGetAllReceivers() {
        List<Receiver> receivers = new ArrayList<>();
        receivers.add(receiver);

        when(receiverRepository.findAll()).thenReturn(receivers);
        when(receiverMapper.toDto(receiver)).thenReturn(receiverDto);

        List<ReceiverDto> result = receiverService.getAllReceivers();

        assertEquals(1, result.size());
        assertEquals(receiverDto, result.get(0));
        verify(receiverRepository, times(1)).findAll();
    }

    @Test
    void testGetReceiverById() {
        when(receiverRepository.findById(1L)).thenReturn(Optional.of(receiver));
        when(receiverMapper.toDto(receiver)).thenReturn(receiverDto);

        ReceiverDto result = receiverService.getReceiverById(1L);

        assertNotNull(result);
        assertEquals(receiverDto, result);
        verify(receiverRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateReceiver() {
        when(receiverMapper.toEntity(receiverDto)).thenReturn(receiver);
        when(receiverRepository.save(receiver)).thenReturn(receiver);
        when(receiverMapper.toDto(receiver)).thenReturn(receiverDto);

        ReceiverDto result = receiverService.createReceiver(receiverDto);

        assertNotNull(result);
        assertEquals(receiverDto, result);
        verify(receiverMapper, times(1)).toEntity(receiverDto);
        verify(receiverRepository, times(1)).save(receiver);
    }

    @Test
    void testDeleteReceiver() {
        when(receiverRepository.existsById(1L)).thenReturn(true);

        receiverService.deleteReceiver(1L);

        verify(receiverRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteReceiverNotFound() {
        when(receiverRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            receiverService.deleteReceiver(1L);
        });

        assertEquals("Receiver not found with id: 1", exception.getMessage());
        verify(receiverRepository, never()).deleteById(any());
    }
}
