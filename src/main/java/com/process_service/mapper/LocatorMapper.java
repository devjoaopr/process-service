package com.process_service.mapper;

import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.entity.Locators;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocatorMapper {
    LocatorDTO toDto(Locators locator);

    Locators toEntity(LocatorDTO dto);

    LocatorResponse toResponse(Locators locator);

    UpdateLocatorRequest toUpdateDistrictRequest(Locators locator);

    void UpdateEntityFromDto(UpdateLocatorRequest dto, @MappingTarget Locators entity);
}
