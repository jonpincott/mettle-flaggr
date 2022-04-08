package com.example.mettleflaggr.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    Set<UserFlag> flags;

}
