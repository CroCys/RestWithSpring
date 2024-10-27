package org.example.service.impl;

import jakarta.transaction.Transactional;
import org.example.dto.SenderDto;
import org.example.entity.Sender;
import org.example.mapper.SenderMapper;
import org.example.repository.SenderRepository;
import org.example.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SenderServiceImpl implements SenderService {
    private final SenderRepository senderRepository;
    private final SenderMapper senderMapper;

    @Autowired
    public SenderServiceImpl(SenderRepository senderRepository, SenderMapper senderMapper) {
        this.senderRepository = senderRepository;
        this.senderMapper = senderMapper;
    }

    @Override
    public List<SenderDto> getAllSenders() {
        return senderRepository.findAll().stream()
                .map(senderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SenderDto getSenderById(Long id) {
        Sender sender = senderRepository.findById(id).orElseThrow(RuntimeException::new);
        return senderMapper.toDto(sender);
    }

    @Override
    public SenderDto createSender(SenderDto senderDto) {
        Sender sender = senderMapper.toEntity(senderDto);
        Sender savedSender = senderRepository.save(sender);
        return senderMapper.toDto(savedSender);
    }

    @Override
    public void deleteSender(Long id) {
        if (!senderRepository.existsById(id)) {
            throw new RuntimeException("Sender not found with id: " + id);
        }
        senderRepository.deleteById(id);
    }
}
