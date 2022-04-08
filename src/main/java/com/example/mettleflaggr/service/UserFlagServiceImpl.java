package com.example.mettleflaggr.service;

import com.example.mettleflaggr.dao.FlagRepository;
import com.example.mettleflaggr.dao.UserFlagRepository;
import com.example.mettleflaggr.dao.UserRepository;
import com.example.mettleflaggr.dto.UserFlagDto;
import com.example.mettleflaggr.mapper.UserFlagMapper;
import com.example.mettleflaggr.model.UserFlag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFlagServiceImpl implements UserFlagService {

    private final UserFlagRepository userFlagRepository;
    private final UserRepository     userRepository;
    private final FlagRepository     flagRepository;
    private final UserFlagMapper     userFlagMapper;

    @Override
    public UserFlagDto setUserFlagState(UserFlagDto dto) {
        final var username = dto.getUser();
        final var flag = dto.getFlag();
        final var model = userFlagRepository.findByUserUsernameAndFlagName(username, flag)
                .orElseGet(() -> new UserFlag()
                        .setUser(userRepository.findByUsername(username).orElseThrow())
                        .setFlag(flagRepository.findByName(flag).orElseThrow())
                );
        model.setState(dto.getState());
        return userFlagMapper.toDto(userFlagRepository.save(model));
    }

}
