package com.process_service.mapper;

import com.process_service.dto.*;
import com.process_service.entity.ProcessSituation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessSituationMapper {
    ProcessSituationDTO toDto(ProcessSituation processSituation);

    ProcessSituation toEntity(ProcessSituationDTO dto);

    ProcessSituationResponse toResponse(ProcessSituation processSituation);

    UpdateProcessSituationRequest toUpdateRequest(ProcessSituation processSituation);

    List<ProcessSituationDTO> toDtoList(List<ProcessSituation> processesSituation);

    void updateEntityFromDto(UpdateProcessSituationRequest dto, @MappingTarget ProcessSituation entity);

}
