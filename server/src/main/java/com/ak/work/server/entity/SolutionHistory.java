package com.ak.work.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "solution_history")
public class SolutionHistory {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "solution_history_id_sequence")
    private int id;

    @Column(name = "problem_id")
    private int problem;

    @Column(name = "user_id")
    private int user;

    @Column(name = "row_")
    private int row;

    @Column(name = "column_")
    private int column;

    @Column(name = "value_")
    private int value;
}
