package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.Detail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetailMapper {
    DetailDTO toDto(Detail detail);

    Detail toEntity(DetailDTO dto);

    DetailResponse toResponse(Detail detail);

    UpdateDetailRequest toUpdateDistrictRequest(Detail detail);

    void UpdateEntityFromDto(UpdateDetailRequest dto, @MappingTarget Detail entity);
}
