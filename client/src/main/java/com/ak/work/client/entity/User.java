package com.ak.work.client.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"

)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Admin.class, name = "admin"),
        @JsonSubTypes.Type(value = Expert.class, name = "expert"),
        @JsonSubTypes.Type(value = Manager.class, name = "manager")
})
public abstract class User {

    private int id;

    private String username;

    private String email;

    private String password;

    private Date registrationDate;
}
