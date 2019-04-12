package com.ak.work.server.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "problem")
public class Problem {

    @Id
    @GeneratedValue(generator = "problem_id_sequence")
    private int id;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Solution> solutions = new ArrayList<>();
}
