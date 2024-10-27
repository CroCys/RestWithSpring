package org.example.controller;

import org.example.dto.SenderDto;
import org.example.service.SenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/senders")
public class SenderController {
    private final SenderService senderService;

    public SenderController(SenderService senderService) {
        this.senderService = senderService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SenderDto> getAllSenders() {
        return senderService.getAllSenders();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SenderDto> getSenderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(senderService.getSenderById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SenderDto> createSender(@RequestBody SenderDto senderDto) {
        SenderDto createdSender = senderService.createSender(senderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSender);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSender(@PathVariable("id") Long id) {
        senderService.deleteSender(id);
        return ResponseEntity.noContent().build();
    }
}
