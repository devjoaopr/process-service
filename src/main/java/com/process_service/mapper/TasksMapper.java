package com.process_service.mapper;

import com.process_service.dto.Cases.CasesDTO;
import com.process_service.dto.Cases.CasesResponse;
import com.process_service.dto.Cases.UpdateCasesRequest;
import com.process_service.dto.Tasks.TaskDTO;
import com.process_service.dto.Tasks.TaskResponse;
import com.process_service.dto.Tasks.UpdateTaskRequest;
import com.process_service.entity.Cases;
import com.process_service.entity.Tasks;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TasksMapper {

    TaskDTO toDto(Tasks tasks);

    Tasks toEntity(TaskDTO dto);

    TaskResponse toResponse(Tasks tasks);

    UpdateTaskRequest toUpdateDistrictRequest(Tasks tasks);

    void UpdateEntityFromDto(UpdateTaskRequest dto, @MappingTarget Tasks entity);

}
