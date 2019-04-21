package com.ak.work.client.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("expert")
public class Expert extends User {

    private int prevProjects;

    private int experienceAsExpert;
}
