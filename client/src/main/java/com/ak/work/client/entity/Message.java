package com.ak.work.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Long id;
    private String name;
    private String message;

    public Message(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
