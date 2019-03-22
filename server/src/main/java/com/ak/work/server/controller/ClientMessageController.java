package com.ak.work.server.controller;

import com.ak.work.server.entity.ClientMessage;
import com.ak.work.server.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.urn}/client-message")
public class ClientMessageController {

    @Autowired
    private ClientMessageService service;

    @GetMapping(value = "/all")
    public ResponseEntity<Iterable<ClientMessage>> getAllClientMessages() {
        Iterable<ClientMessage> messages = service.getAllClientMessages();
        return ResponseEntity.ok(messages);
    }

    @PostMapping
    public ResponseEntity<ClientMessage> saveClientMessage(@Valid @RequestBody ClientMessage clientMessage) {
        ClientMessage savedMessage = service.saveClientMessage(clientMessage);
        return ResponseEntity.ok(savedMessage);
    }
}
