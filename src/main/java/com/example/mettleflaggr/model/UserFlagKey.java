package com.example.mettleflaggr.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class UserFlagKey implements Serializable {

    @NotNull
    Long userId;

    @NotNull
    Long flagId;

}
