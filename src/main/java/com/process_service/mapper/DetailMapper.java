package com.process_service.mapper;

import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.entity.Details;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DetailMapper {
    DetailDTO toDto(Details detail);

    Details toEntity(DetailDTO dto);

    DetailResponse toResponse(Details detail);

    UpdateDetailRequest toUpdateDetailRequest(Details detail);

    void UpdateEntityFromDto(UpdateDetailRequest dto, @MappingTarget Details detail);
}
