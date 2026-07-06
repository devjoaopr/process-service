package com.process_service.mapper;

import com.process_service.dto.ProcessSituation.ProcessSituationDTO;
import com.process_service.dto.ProcessSituation.ProcessSituationResponse;
import com.process_service.dto.ProcessSituation.UpdateProcessSituationRequest;
import com.process_service.entity.ProcessSituations;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessSituationMapper {
    ProcessSituationDTO toDto(ProcessSituations processSituation);

    ProcessSituations toEntity(ProcessSituationDTO dto);

    ProcessSituationResponse toResponse(ProcessSituations processSituation);

    UpdateProcessSituationRequest toUpdateRequest(ProcessSituations processSituation);

    List<ProcessSituationDTO> toDtoList(List<ProcessSituations> processesSituation);

    void updateEntityFromDto(UpdateProcessSituationRequest dto, @MappingTarget ProcessSituations entity);

}
