package com.ak.work.server.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class),
        @JsonSubTypes.Type(value = Expert.class),
        @JsonSubTypes.Type(value = Manager.class)
})
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(generator = "user_id_sequence")
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "blocked")
    private Boolean isBlocked;
}
