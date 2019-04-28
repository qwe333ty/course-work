package com.ak.work.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem {

    private int id;

    private User manager;

    private String header;

    private Boolean resolved;
}
