package org.example.controller;

import org.example.dto.SenderDto;
import org.example.service.SenderService;
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

public class SenderControllerTest {
    @Mock
    private SenderService senderService;

    @InjectMocks
    private SenderController senderController;

    private SenderDto senderDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Инициализируем тестовые данные
        senderDto = new SenderDto();
        senderDto.setId(1L);
        senderDto.setName("Test Sender");
    }

    @Test
    void testGetAllSenders() {
        List<SenderDto> senders = new ArrayList<>();
        senders.add(senderDto);

        when(senderService.getAllSenders()).thenReturn(senders);

        List<SenderDto> result = senderController.getAllSenders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(senderDto, result.get(0));
        verify(senderService, times(1)).getAllSenders();
    }

    @Test
    void testGetSenderById() {
        when(senderService.getSenderById(1L)).thenReturn(senderDto);

        ResponseEntity<SenderDto> response = senderController.getSenderById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(senderDto, response.getBody());
        verify(senderService, times(1)).getSenderById(1L);
    }

    @Test
    void testCreateSender() {
        when(senderService.createSender(any(SenderDto.class))).thenReturn(senderDto);

        ResponseEntity<SenderDto> response = senderController.createSender(senderDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(senderDto, response.getBody());
        verify(senderService, times(1)).createSender(any(SenderDto.class));
    }

    @Test
    void testDeleteSender() {
        doNothing().when(senderService).deleteSender(1L);

        ResponseEntity<Void> response = senderController.deleteSender(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(senderService, times(1)).deleteSender(1L);
    }
}
