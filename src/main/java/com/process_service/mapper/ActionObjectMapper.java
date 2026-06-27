package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActionObjectMapper {
    ActionObjectDTO toDto(ActionObject actionObject);

    ActionObject toEntity(ActionObjectDTO dto);

    ActionObjectResponse toResponse(ActionObject actionObject);

    UpdateActionObjectRequest toUpdateDistrictRequest(ActionObject actionObject);

    void UpdateEntityFromDto(UpdateActionObjectRequest dto, @MappingTarget ActionObject entity);
}
