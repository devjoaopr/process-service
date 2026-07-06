package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.entity.ActionObjects;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActionObjectMapper {
    ActionObjectDTO toDto(ActionObjects actionObject);

    ActionObjects toEntity(ActionObjectDTO dto);

    ActionObjectResponse toResponse(ActionObjects actionObject);

    UpdateActionObjectRequest toUpdateDistrictRequest(ActionObjects actionObject);

    void UpdateEntityFromDto(UpdateActionObjectRequest dto, @MappingTarget ActionObjects entity);
}
