package com.process_service.mapper;
import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.Conference;
import com.process_service.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConferenceMapper {
    ConferenceDTO toDto(Conference conference);

    Conference toEntity(ConferenceDTO dto);

    ConferenceResponse toResponse(Conference conference);

    UpdateConferenceRequest UpdateConferenceRequest(Conference conference);

    void UpdateEntityFromDto(UpdateConferenceRequest dto, @MappingTarget Conference conference);
}
