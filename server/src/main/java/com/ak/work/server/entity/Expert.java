package com.ak.work.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "experts")
@EqualsAndHashCode(callSuper = true)
public class Expert extends User {

    @Column(name = "prev_projects")
    private int prevProjects;

    @Column(name = "expert_experience")
    private Integer experienceAsExpert;
}
