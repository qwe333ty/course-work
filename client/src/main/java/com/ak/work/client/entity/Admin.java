package com.ak.work.client.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("admin")
public class Admin extends User {

    private String bossEmail;

    private String address;
}
