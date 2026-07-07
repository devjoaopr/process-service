package com.process_service.mapper;

import com.process_service.dto.Cases.CasesDTO;
import com.process_service.dto.Cases.CasesResponse;
import com.process_service.dto.Cases.UpdateCasesRequest;
import com.process_service.entity.Cases;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CasesMapper {
    CasesDTO toDto(Cases cases);

    Cases toEntity(CasesDTO dto);

    CasesResponse toResponse(Cases cases);

    UpdateCasesRequest toUpdateDistrictRequest(Cases cases);

    void UpdateEntityFromDto(UpdateCasesRequest dto, @MappingTarget Cases entity);
}
