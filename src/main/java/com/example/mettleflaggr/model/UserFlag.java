package com.example.mettleflaggr.model;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.NotNull;


@Data
@Entity
public class UserFlag {

    @EmbeddedId
    final UserFlagKey id = new UserFlagKey();

    @ManyToOne
    @MapsId("userId")
    @NotNull
    User user;

    public UserFlag setUser(User user) {
        this.user = user;
        this.id.setUserId(user.getId());
        return this;
    }

    @ManyToOne
    @MapsId("flagId")
    @NotNull
    Flag flag;

    public UserFlag setFlag(Flag flag) {
        this.flag = flag;
        this.id.setFlagId(flag.getId());
        return this;
    }

    @NotNull
    Boolean state;

}
