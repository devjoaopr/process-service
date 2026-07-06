package com.process_service.mapper;
import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.entity.Conferences;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConferenceMapper {
    ConferenceDTO toDto(Conferences conference);

    Conferences toEntity(ConferenceDTO dto);

    ConferenceResponse toResponse(Conferences conference);

    UpdateConferenceRequest UpdateConferenceRequest(Conferences conference);

    void UpdateEntityFromDto(UpdateConferenceRequest dto, @MappingTarget Conferences conference);
}
