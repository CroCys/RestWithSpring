package org.example.controller;

import org.example.dto.ReceiverDto;
import org.example.service.ReceiverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receivers")
public class ReceiverController {
    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ReceiverDto> getAllReceivers() {
        return receiverService.getAllReceivers();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceiverDto> getReceiverById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(receiverService.getReceiverById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReceiverDto> createReceiver(@RequestBody ReceiverDto receiverDto) {
        ReceiverDto createdReceiver = receiverService.createReceiver(receiverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReceiver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceiver(@PathVariable("id") Long id) {
        receiverService.deleteReceiver(id);
        return ResponseEntity.noContent().build();
    }
}
