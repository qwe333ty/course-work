package com.ak.work.server.service;

import com.ak.work.server.entity.ClientMessage;

public interface ClientMessageService {
    Iterable<ClientMessage> getAllClientMessages();

    ClientMessage saveClientMessage(ClientMessage message);
}
