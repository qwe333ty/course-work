package com.ak.work.client.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("manager")
public class Manager extends User {

    private String company;

    private int experienceAsManager;
}
