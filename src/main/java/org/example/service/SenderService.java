package org.example.service;

import org.example.dto.SenderDto;

import java.util.List;

public interface SenderService {
    List<SenderDto> getAllSenders();

    SenderDto getSenderById(Long id);

    SenderDto createSender(SenderDto senderDto);

    void deleteSender(Long id);
}
