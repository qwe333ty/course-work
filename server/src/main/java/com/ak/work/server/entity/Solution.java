package com.ak.work.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "solution")
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "solution_order")
    private Integer order;
}
