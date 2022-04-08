package com.example.mettleflaggr.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class Flag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotBlank
    private String name;

    @Column
    @NotNull
    private Boolean state;

    @OneToMany(mappedBy = "flag")
    @ToString.Exclude
    Set<UserFlag> users;

}
