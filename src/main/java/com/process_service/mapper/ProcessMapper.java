package com.process_service.mapper;

import com.process_service.dto.Process.ProcessDTO;
import com.process_service.dto.Process.ProcessResponse;
import com.process_service.dto.Process.UpdateProcessRequest;
import com.process_service.entity.Process;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    ProcessDTO toDto(Process process);

    Process toEntity(ProcessDTO dto);

    ProcessResponse toResponse(Process process);

    UpdateProcessRequest toUpdateRequest(Process process);

    List<ProcessDTO> toDtoList(List<Process> processes);

    void updateEntityFromDto(UpdateProcessRequest dto, @MappingTarget Process entity);

}