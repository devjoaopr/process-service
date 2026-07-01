package com.process_service.services;

import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupFilter;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.entity.Group;
import com.process_service.mapper.GroupMapper;
import com.process_service.repository.GroupRepository;
import com.process_service.shared.ResourceNotFoundException;
import com.process_service.shared.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class GroupService {
    @Autowired
    GroupRepository repository;
    @Autowired
    GroupMapper mapper;


    public GroupResponse createGroup(GroupDTO groupDTO) {

        Group group = mapper.toEntity(groupDTO);

        group.setId(UUID.randomUUID());
        group.setCreatedAt(OffsetDateTime.now());

        Group saved = repository.save(group);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Group group = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        group.setDeletedAt(OffsetDateTime.now());
        repository.delete(group);

    }

    public GroupResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public GroupResponse updateById(UUID id, UpdateGroupRequest request) {
        Group group = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, group);
        group.setUpdatedAt(OffsetDateTime.now());
        Group saved = repository.save(group);

        return mapper.toResponse(saved);
    }

    public Page<GroupResponse> findAll(GroupFilter filter, Pageable pageable) {
        Specification<Group> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
