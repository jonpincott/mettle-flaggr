package com.example.mettleflaggr.mapper;

import com.example.mettleflaggr.dto.UserDto;
import com.example.mettleflaggr.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User fromDto(UserDto userDto);

}
