package com.process_service.mapper;

import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.Districts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DistrictMapper {
    DistrictDTO toDto(Districts district);

    Districts toEntity(DistrictDTO dto);

    DistrictResponse toResponse(Districts district);

    UpdateDistrictRequest toUpdateDistrictRequest(Districts district);

    void UpdateEntityFromDto(UpdateDistrictRequest dto, @MappingTarget Districts entity);
}
