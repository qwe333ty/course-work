package com.ak.work.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "client_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientMessage {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "client_id_sequence")
    private long id;

    @NotNull(message = "Name is null")
    @NotEmpty(message = "Name must not be empty")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Message is null")
    @NotEmpty(message = "Message must not be empty")
    @Column(name = "message")
    private String message;
}
