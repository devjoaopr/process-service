package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.Locator;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocatorMapper {
    LocatorDTO toDto(Locator locator);

    Locator toEntity(LocatorDTO dto);

    LocatorResponse toResponse(Locator locator);

    UpdateLocatorRequest toUpdateDistrictRequest(Locator locator);

    void UpdateEntityFromDto(UpdateLocatorRequest dto, @MappingTarget Locator entity);
}
