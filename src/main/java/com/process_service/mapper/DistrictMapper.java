package com.process_service.mapper;

import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DistrictMapper {
    DistrictDTO toDto(District district);

    District toEntity(DistrictDTO dto);

    DistrictResponse toResponse(District district);

    UpdateDistrictRequest toUpdateDistrictRequest(District district);

    void UpdateEntityFromDto(UpdateDistrictRequest dto, @MappingTarget District entity);
}
