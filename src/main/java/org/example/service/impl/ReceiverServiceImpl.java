package org.example.service.impl;

import jakarta.transaction.Transactional;
import org.example.dto.ReceiverDto;
import org.example.entity.Receiver;
import org.example.mapper.ReceiverMapper;
import org.example.repository.ReceiverRepository;
import org.example.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepository receiverRepository;
    private final ReceiverMapper receiverMapper;

    @Autowired
    public ReceiverServiceImpl(ReceiverRepository receiverRepository, ReceiverMapper receiverMapper) {
        this.receiverRepository = receiverRepository;
        this.receiverMapper = receiverMapper;
    }

    @Override
    public List<ReceiverDto> getAllReceivers() {
        return receiverRepository.findAll().stream()
                .map(receiverMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReceiverDto getReceiverById(Long id) {
        Receiver receiver = receiverRepository.findById(id).orElseThrow(RuntimeException::new);
        return receiverMapper.toDto(receiver);
    }

    @Override
    public ReceiverDto createReceiver(ReceiverDto receiverDto) {
        Receiver receiver = receiverMapper.toEntity(receiverDto);
        Receiver savedReceiver = receiverRepository.save(receiver);
        return receiverMapper.toDto(savedReceiver);
    }

    @Override
    public void deleteReceiver(Long id) {
        if (!receiverRepository.existsById(id)) {
            throw new RuntimeException("Receiver not found with id: " + id);
        }
        receiverRepository.deleteById(id);
    }
}
