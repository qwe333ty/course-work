package com.ak.work.server.repository;

import com.ak.work.server.entity.ClientMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMessageRepository extends CrudRepository<ClientMessage, Long> {
}
