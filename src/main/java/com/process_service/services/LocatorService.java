package com.process_service.services;

import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorFilter;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.entity.Locator;
import com.process_service.mapper.LocatorMapper;
import com.process_service.repository.LocatorRepository;
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
public class LocatorService {
    @Autowired
    LocatorRepository repository;
    @Autowired
    LocatorMapper mapper;


    public LocatorResponse create(LocatorDTO locatorDTO) {

        Locator locator = mapper.toEntity(locatorDTO);

        locator.setId(UUID.randomUUID());
        locator.setCreatedAt(OffsetDateTime.now());

        Locator saved = repository.save(locator);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Locator locator = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        locator.setDeletedAt(OffsetDateTime.now());
        repository.delete(locator);

    }

    public LocatorResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public LocatorResponse updateById(UUID id, UpdateLocatorRequest request) {
        Locator locator = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, locator);
        locator.setUpdatedAt(OffsetDateTime.now());
        Locator saved = repository.save(locator);

        return mapper.toResponse(saved);
    }


    public Page<LocatorResponse> findAll(LocatorFilter filter, Pageable pageable) {
        Specification<Locator> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
