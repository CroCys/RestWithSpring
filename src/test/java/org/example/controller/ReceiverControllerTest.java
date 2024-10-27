package org.example.controller;

import org.example.dto.ReceiverDto;
import org.example.service.ReceiverService;
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

public class ReceiverControllerTest {
    @Mock
    private ReceiverService receiverService;

    @InjectMocks
    private ReceiverController receiverController;

    private ReceiverDto receiverDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Инициализируем тестовые данные
        receiverDto = new ReceiverDto();
        receiverDto.setId(1L);
        receiverDto.setName("Test Receiver");
    }

    @Test
    void testGetAllReceivers() {
        List<ReceiverDto> receivers = new ArrayList<>();
        receivers.add(receiverDto);

        when(receiverService.getAllReceivers()).thenReturn(receivers);

        List<ReceiverDto> result = receiverController.getAllReceivers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(receiverDto, result.get(0));
        verify(receiverService, times(1)).getAllReceivers();
    }

    @Test
    void testGetReceiverById() {
        when(receiverService.getReceiverById(1L)).thenReturn(receiverDto);

        ResponseEntity<ReceiverDto> response = receiverController.getReceiverById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(receiverDto, response.getBody());
        verify(receiverService, times(1)).getReceiverById(1L);
    }

    @Test
    void testCreateReceiver() {
        when(receiverService.createReceiver(any(ReceiverDto.class))).thenReturn(receiverDto);

        ResponseEntity<ReceiverDto> response = receiverController.createReceiver(receiverDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(receiverDto, response.getBody());
        verify(receiverService, times(1)).createReceiver(any(ReceiverDto.class));
    }

    @Test
    void testDeleteReceiver() {
        doNothing().when(receiverService).deleteReceiver(1L);

        ResponseEntity<Void> response = receiverController.deleteReceiver(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(receiverService, times(1)).deleteReceiver(1L);
    }
}
