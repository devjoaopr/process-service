package com.process_service.mapper;

import com.process_service.dto.PracticeArea.PracticeAreaDTO;
import com.process_service.dto.PracticeArea.PracticeAreaResponse;
import com.process_service.dto.PracticeArea.UpdatePracticeAreaRequest;
import com.process_service.entity.PracticeAreas;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PracticeAreaMapper {
    PracticeAreaDTO toDto(PracticeAreas practiceArea);

    PracticeAreas toEntity(PracticeAreaDTO dto);

    PracticeAreaResponse toResponse(PracticeAreas practiceArea);

    UpdatePracticeAreaRequest toUpdateDistrictRequest(PracticeAreas practiceArea);

    void UpdateEntityFromDto(UpdatePracticeAreaRequest dto, @MappingTarget PracticeAreas entity);
}
