package com.process_service.mapper;

import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.entity.Origins;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OriginMapper {
    OriginDTO toDto(Origins origin);

    Origins toEntity(OriginDTO dto);

    OriginResponse toResponse(Origins origin);

    UpdateOriginRequest toUpdateDistrictRequest(Origins origin);

    void UpdateEntityFromDto(UpdateOriginRequest dto, @MappingTarget Origins entity);
}
