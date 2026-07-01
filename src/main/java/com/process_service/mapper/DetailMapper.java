package com.process_service.mapper;

import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.Detail;
import com.process_service.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetailMapper {
    DetailDTO toDto(Detail detail);

    Detail toEntity(DetailDTO dto);

    DetailResponse toResponse(Detail detail);

    UpdateDetailRequest toUpdateDetailRequest(Detail detail);

    void UpdateEntityFromDto(UpdateDetailRequest dto, @MappingTarget Detail detail);
}
