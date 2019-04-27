package com.ak.work.client.entity;


import lombok.Data;

@Data
public class SolutionHistory {

    private int id;

    private int problem;

    private int user;

    private int row;

    private int column;

    private int value;
}

