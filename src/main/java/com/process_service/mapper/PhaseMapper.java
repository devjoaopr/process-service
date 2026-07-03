package com.process_service.mapper;

import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.entity.Phases;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PhaseMapper {
    PhaseDTO toDto(Phases phase);

    Phases toEntity(PhaseDTO dto);

    PhaseResponse toResponse(Phases phase);

    UpdatePhaseRequest toUpdateDistrictRequest(Phases phase);

    void UpdateEntityFromDto(UpdatePhaseRequest dto, @MappingTarget Phases entity);
}
