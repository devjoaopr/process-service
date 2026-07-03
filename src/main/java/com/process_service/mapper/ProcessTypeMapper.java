package com.process_service.mapper;

import com.process_service.dto.ProcessType.ProcessTypeDTO;
import com.process_service.dto.ProcessType.ProcessTypeResponse;
import com.process_service.dto.ProcessType.UpdateProcessTypeRequest;
import com.process_service.entity.ProcessTypes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessTypeMapper {
    ProcessTypeDTO toDto(ProcessTypes processType);

    ProcessTypes toEntity(ProcessTypeDTO dto);

    ProcessTypeResponse toResponse(ProcessTypes processType);

    UpdateProcessTypeRequest toUpdateProcessTypeRequest(ProcessTypes processType);

    void UpdateEntityFromDto(UpdateProcessTypeRequest dto, @MappingTarget ProcessTypes processType);
}