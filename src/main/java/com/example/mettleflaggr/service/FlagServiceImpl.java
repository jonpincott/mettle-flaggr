package com.example.mettleflaggr.service;

import com.example.mettleflaggr.dao.FlagRepository;
import com.example.mettleflaggr.dto.FlagDto;
import com.example.mettleflaggr.mapper.FlagMapper;
import com.example.mettleflaggr.model.Flag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlagServiceImpl implements FlagService {

    private final FlagMapper     flagMapper;
    private final FlagRepository flagRepository;

    @Override
    public List<FlagDto> findActiveFlagsByUsername(String username) {
        return flagRepository.findActiveFlagNamesByUsername(username).stream()
                .map(flagMapper::toDto)
                .toList();
    }

    @Override
    public FlagDto createFlag(FlagDto flagDto) {
        var flag = flagMapper.fromDto(flagDto);
        if (null == flag.getState())
            flag.setState(false);
        flag = flagRepository.save(flag);
        return flagMapper.toDto(flag);
    }

    @Override
    public List<FlagDto> findAllFlags() {
        return flagRepository.findAll().stream()
                .map(flagMapper::toDto)
                .toList();
    }

}
