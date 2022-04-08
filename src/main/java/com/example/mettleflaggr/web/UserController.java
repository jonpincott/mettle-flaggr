package com.example.mettleflaggr.web;

import com.example.mettleflaggr.dto.FlagDto;
import com.example.mettleflaggr.dto.UserDto;
import com.example.mettleflaggr.service.FlagService;
import com.example.mettleflaggr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FlagService flagService;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/me/flag")
    public List<FlagDto> getActiveFlagsForUser(Principal principal) {
        return flagService.findActiveFlagsByUsername(principal.getName());
    }

}
