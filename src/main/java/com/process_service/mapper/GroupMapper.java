package com.process_service.mapper;

import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.entity.Groups;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {
    GroupDTO toDto(Groups group);

    Groups toEntity(GroupDTO dto);

    GroupResponse toResponse(Groups group);

    UpdateGroupRequest toUpdateDistrictRequest(Groups group);

    void UpdateEntityFromDto(UpdateGroupRequest dto, @MappingTarget Groups entity);
}
