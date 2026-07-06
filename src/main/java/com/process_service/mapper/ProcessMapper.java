package com.process_service.mapper;

import com.process_service.dto.Process.ProcessDTO;
import com.process_service.dto.Process.ProcessResponse;
import com.process_service.dto.Process.UpdateProcessRequest;
import com.process_service.entity.Processes;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProcessMapper {

    ProcessDTO toDto(Processes process);

    Processes toEntity(ProcessDTO dto);

    ProcessResponse toResponse(Processes process);

    UpdateProcessRequest toUpdateRequest(Processes process);

    List<ProcessDTO> toDtoList(List<Processes> processes);

    void updateEntityFromDto(UpdateProcessRequest dto, @MappingTarget Processes entity);

}