package com.ak.work.server.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "managers")
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("manager")
public class Manager extends User {

    @Column(name = "company")
    private String company;

    @Column(name = "manager_experience")
    private int experienceAsManager;
}
