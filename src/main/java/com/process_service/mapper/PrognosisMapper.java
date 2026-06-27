package com.process_service.mapper;

import com.process_service.dto.PracticeArea.PracticeAreaDTO;
import com.process_service.dto.PracticeArea.PracticeAreaResponse;
import com.process_service.dto.PracticeArea.UpdatePracticeAreaRequest;
import com.process_service.dto.Prognosis.PrognosisDTO;
import com.process_service.dto.Prognosis.PrognosisResponse;
import com.process_service.dto.Prognosis.UpdatePrognosisRequest;
import com.process_service.entity.PracticeArea;
import com.process_service.entity.Prognosis;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PrognosisMapper {
    PrognosisDTO toDto(Prognosis prognosis);

    Prognosis toEntity(PrognosisDTO dto);

    PrognosisResponse toResponse(Prognosis prognosis);

    UpdatePrognosisRequest toUpdateDistrictRequest(Prognosis prognosis);

    void UpdateEntityFromDto(UpdatePrognosisRequest dto, @MappingTarget Prognosis entity);
}
