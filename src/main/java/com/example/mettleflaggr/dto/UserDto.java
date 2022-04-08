package com.example.mettleflaggr.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;

@Value
@Builder
@Jacksonized
public class UserDto {

    @NotEmpty
    String username;

}
