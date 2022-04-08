package com.example.mettleflaggr.service;

import com.example.mettleflaggr.dto.FlagDto;

import java.util.List;

public interface FlagService {

    List<FlagDto> findActiveFlagsByUsername(String username);

    FlagDto createFlag(FlagDto flagDto);

    List<FlagDto> findAllFlags();
}
