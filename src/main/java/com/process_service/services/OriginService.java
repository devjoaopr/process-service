package com.process_service.services;

import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginFilter;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.entity.Origin;
import com.process_service.mapper.OriginMapper;
import com.process_service.repository.OriginRepository;
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
public class OriginService {
    @Autowired
    OriginRepository repository;
    @Autowired
    OriginMapper mapper;


    public OriginResponse create(OriginDTO originDTO) {

        Origin origin = mapper.toEntity(originDTO);

        origin.setId(UUID.randomUUID());
        origin.setCreatedAt(OffsetDateTime.now());

        Origin saved = repository.save(origin);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Origin origin = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        origin.setDeletedAt(OffsetDateTime.now());
        repository.delete(origin);

    }

    public OriginResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public OriginResponse updateById(UUID id, UpdateOriginRequest request) {
        Origin origin = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, origin);
        origin.setUpdatedAt(OffsetDateTime.now());
        Origin saved = repository.save(origin);

        return mapper.toResponse(saved);
    }

    public Page<OriginResponse> findAll(OriginFilter filter, Pageable pageable) {
        Specification<Origin> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
