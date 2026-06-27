package com.process_service.mapper;

import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.dto.PracticeArea.PracticeAreaDTO;
import com.process_service.dto.PracticeArea.PracticeAreaResponse;
import com.process_service.dto.PracticeArea.UpdatePracticeAreaRequest;
import com.process_service.entity.Phase;
import com.process_service.entity.PracticeArea;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PracticeAreaMapper {
    PracticeAreaDTO toDto(PracticeArea practiceArea);

    PracticeArea toEntity(PracticeAreaDTO dto);

    PracticeAreaResponse toResponse(PracticeArea practiceArea);

    UpdatePracticeAreaRequest toUpdateDistrictRequest(PracticeArea practiceArea);

    void UpdateEntityFromDto(UpdatePracticeAreaRequest dto, @MappingTarget PracticeArea entity);
}
