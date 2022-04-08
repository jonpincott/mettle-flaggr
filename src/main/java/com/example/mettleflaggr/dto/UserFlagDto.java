package com.example.mettleflaggr.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@Builder
@Jacksonized
public class UserFlagDto {

    @NotBlank
    String user;

    @NotBlank
    String flag;

    @NotNull
    Boolean state;

}
