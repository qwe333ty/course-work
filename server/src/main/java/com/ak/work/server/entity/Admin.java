package com.ak.work.server.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Data
@Entity
@Table(name = "admins")
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("admin")
public class Admin extends User {

    @Email
    @Column(name = "boss_email")
    private String bossEmail;

    @Column(name = "address")
    private String address;
}
