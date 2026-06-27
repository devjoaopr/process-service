package com.process_service.mapper;

import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.entity.Origin;
import com.process_service.entity.Phase;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PhaseMapper {
    PhaseDTO toDto(Phase phase);

    Phase toEntity(PhaseDTO dto);

    PhaseResponse toResponse(Phase phase);

    UpdatePhaseRequest toUpdateDistrictRequest(Phase phase);

    void UpdateEntityFromDto(UpdatePhaseRequest dto, @MappingTarget Phase entity);
}
