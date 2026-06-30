package com.process_service.mapper;

import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.entity.Locator;
import com.process_service.entity.Origin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OriginMapper {
    OriginDTO toDto(Origin origin);

    Origin toEntity(OriginDTO dto);

    OriginResponse toResponse(Origin origin);

    UpdateOriginRequest toUpdateDistrictRequest(Origin origin);

    void UpdateEntityFromDto(UpdateOriginRequest dto, @MappingTarget Origin entity);
}
