package com.ak.work.server.service.impl;

import com.ak.work.server.entity.ClientMessage;
import com.ak.work.server.repository.ClientMessageRepository;
import com.ak.work.server.service.ClientMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientMessageServiceImpl implements ClientMessageService {

    @Autowired
    private ClientMessageRepository repository;

    @Override
    public Iterable<ClientMessage> getAllClientMessages() {
        return repository.findAll();
    }

    @Override
    public ClientMessage saveClientMessage(ClientMessage message) {
        return repository.save(message);
    }
}
