package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.Conference;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConferenceMapper {
    ConferenceDTO toDto(Conference conference);

    Conference toEntity(ConferenceDTO dto);

    ConferenceResponse toResponse(Conference conference);

    UpdateConferenceRequest toUpdateDistrictRequest(Conference conference);

    void UpdateEntityFromDto(UpdateConferenceRequest dto, @MappingTarget Conference entity);
}
