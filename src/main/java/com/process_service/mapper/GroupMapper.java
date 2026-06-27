package com.process_service.mapper;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {
    GroupDTO toDto(Group group);

    Group toEntity(GroupDTO dto);

    GroupResponse toResponse(Group group);

    UpdateGroupRequest toUpdateDistrictRequest(Group group);

    void UpdateEntityFromDto(UpdateGroupRequest dto, @MappingTarget Group entity);
}
