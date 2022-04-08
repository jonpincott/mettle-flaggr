package com.example.mettleflaggr.service;

import com.example.mettleflaggr.dao.UserRepository;
import com.example.mettleflaggr.dto.UserDto;
import com.example.mettleflaggr.mapper.UserMapper;
import com.example.mettleflaggr.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper     userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        var user = userMapper.fromDto(userDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(u -> UserDto.builder().username(u.getUsername()).build())
                .toList();
    }

}
