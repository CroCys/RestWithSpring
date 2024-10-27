package org.example.service;

import org.example.dto.ReceiverDto;

import java.util.List;

public interface ReceiverService {
    List<ReceiverDto> getAllReceivers();

    ReceiverDto getReceiverById(Long id);

    ReceiverDto createReceiver(ReceiverDto receiverDto);

    void deleteReceiver(Long id);
}
