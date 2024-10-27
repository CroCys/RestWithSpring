package org.example.service.impl;

import jakarta.transaction.Transactional;
import org.example.dto.MessageDto;
import org.example.entity.Message;
import org.example.mapper.MessageMapper;
import org.example.repository.MessageRepository;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto getMessageById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(RuntimeException::new);
        return messageMapper.toDto(message);
    }

    @Override
    public MessageDto createMessage(MessageDto messageDto) {
        Message message = messageMapper.toEntity(messageDto);
        Message savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }

    @Override
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message not found with id: " + id);
        }
        messageRepository.deleteById(id);
    }
}
