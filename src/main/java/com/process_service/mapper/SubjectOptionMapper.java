package com.process_service.mapper;

import com.process_service.dto.SubjectOption.SubjectOptionDTO;
import com.process_service.dto.SubjectOption.SubjectOptionResponse;
import com.process_service.dto.SubjectOption.UpdateSubjectOptionRequest;
import com.process_service.entity.SubjectOptions;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectOptionMapper {
    SubjectOptions toDto(SubjectOptionDTO subjectOption);

    SubjectOptions toEntity(SubjectOptionDTO dto);

    SubjectOptionResponse toResponse(SubjectOptions subjectOption);

    UpdateSubjectOptionRequest toUpdateDistrictRequest(SubjectOptions subjectOption);

    void UpdateEntityFromDto(UpdateSubjectOptionRequest dto, @MappingTarget SubjectOptions entity);
}
