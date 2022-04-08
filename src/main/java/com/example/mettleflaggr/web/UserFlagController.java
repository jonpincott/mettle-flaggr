package com.example.mettleflaggr.web;

import com.example.mettleflaggr.dto.UserFlagDto;
import com.example.mettleflaggr.service.UserFlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/userflag")
@RequiredArgsConstructor
public class UserFlagController {

    private final UserFlagService userFlagService;

    @PutMapping
    public UserFlagDto setUserFlagState(@Valid @RequestBody UserFlagDto dto) {
        return userFlagService.setUserFlagState(dto);
    }

}
