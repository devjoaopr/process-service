package com.process_service.mapper;

import com.process_service.dto.ProcessDTO;
import com.process_service.dto.ProcessResponse;
import com.process_service.entity.Process;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProcessMapper {

    ProcessDTO toDto(Process process);

    Process toEntity(ProcessDTO dto);

    ProcessResponse toResponse(Process process);

    List<ProcessDTO> toDtoList(List<Process> processes);
}