package com.example.mettleflaggr.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@Value
@Builder
@Jacksonized
public class FlagDto {

    @NotBlank
    String name;

    @With
    Boolean state;

}
