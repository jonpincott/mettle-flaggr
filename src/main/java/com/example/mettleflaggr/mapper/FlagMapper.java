package com.example.mettleflaggr.mapper;

import com.example.mettleflaggr.dto.FlagDto;
import com.example.mettleflaggr.model.Flag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlagMapper {

    FlagMapper INSTANCE = Mappers.getMapper(FlagMapper.class);

    FlagDto toDto(Flag flag);

    Flag fromDto(FlagDto flagDto);

}
