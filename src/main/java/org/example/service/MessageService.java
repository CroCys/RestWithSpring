package org.example.service;

import org.example.dto.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getAllMessages();

    MessageDto getMessageById(Long id);

    MessageDto createMessage(MessageDto messageDto);

    void deleteMessage(Long id);
}
