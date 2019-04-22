package com.ak.work.server.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "solution")
public class Solution {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "solution_id_sequence")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @Column(name = "rating")
    private double rating;

    @Column(name = "header")
    private String header;
}
