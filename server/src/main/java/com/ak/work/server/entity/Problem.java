package com.ak.work.server.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(generator = "problem_id_sequence")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(name = "header")
    private String header;
}
