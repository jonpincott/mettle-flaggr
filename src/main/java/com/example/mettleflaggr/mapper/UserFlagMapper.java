package com.example.mettleflaggr.mapper;

import com.example.mettleflaggr.dto.UserFlagDto;
import com.example.mettleflaggr.model.UserFlag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserFlagMapper {

    UserFlagMapper INSTANCE = Mappers.getMapper(UserFlagMapper.class);

    @Mapping(source = "user.username", target = "user")
    @Mapping(source = "flag.name", target = "flag")
    UserFlagDto toDto(UserFlag model);

}
