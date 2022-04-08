package com.example.mettleflaggr.service;

import com.example.mettleflaggr.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> findAll();

}
