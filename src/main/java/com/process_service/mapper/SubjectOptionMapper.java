package com.process_service.mapper;

import com.process_service.dto.Prognosis.PrognosisDTO;
import com.process_service.dto.Prognosis.PrognosisResponse;
import com.process_service.dto.Prognosis.UpdatePrognosisRequest;
import com.process_service.dto.SubjectOption.SubjectOptionDTO;
import com.process_service.dto.SubjectOption.SubjectOptionResponse;
import com.process_service.dto.SubjectOption.UpdateSubjectOptionRequest;
import com.process_service.entity.Prognosis;
import com.process_service.entity.SubjectOption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectOptionMapper {
    SubjectOption toDto(SubjectOptionDTO subjectOption);

    SubjectOption toEntity(SubjectOptionDTO dto);

    SubjectOptionResponse toResponse(SubjectOption subjectOption);

    UpdateSubjectOptionRequest toUpdateDistrictRequest(SubjectOption subjectOption);

    void UpdateEntityFromDto(UpdateSubjectOptionRequest dto, @MappingTarget SubjectOption entity);
}
