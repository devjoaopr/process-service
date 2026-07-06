package com.process_service.services;

import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupFilter;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.entity.Groups;
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


    public GroupResponse create(GroupDTO groupDTO) {

        Groups group = mapper.toEntity(groupDTO);
        return mapper.toResponse(repository.save(group));
    }

    public void delete(UUID id) {
        Groups group = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        group.setDeletedAt(OffsetDateTime.now());
        repository.save(group);

    }

    public GroupResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public GroupResponse update(UUID id, UpdateGroupRequest request) {
        Groups group = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, group);
        group.setUpdatedAt(OffsetDateTime.now());
        Groups saved = repository.save(group);

        return mapper.toResponse(saved);
    }

    public Page<GroupResponse> findAll(GroupFilter filter, Pageable pageable) {
        Specification<Groups> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
