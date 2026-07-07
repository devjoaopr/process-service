package com.process_service.services;

import com.process_service.dto.Cases.*;
import com.process_service.entity.Cases;
import com.process_service.mapper.CasesMapper;
import com.process_service.repository.CasesRepository;
import com.process_service.shared.ResourceNotFoundException;
import com.process_service.shared.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class CaseService {

    private final CasesRepository repository;
    private final CasesMapper mapper;

    public CaseService(CasesRepository repository, CasesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public CasesResponse create(CasesDTO dto) {

        Cases cases = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(cases));
    }

    public void delete(UUID id) {
        Cases cases = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        cases.setDeletedAt(OffsetDateTime.now());
        repository.save(cases);

    }

    public CasesResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public CasesResponse update(UUID id, UpdateCasesRequest request) {
        Cases cases = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, cases);
        cases.setUpdatedAt(OffsetDateTime.now());
        Cases saved = repository.save(cases);

        return mapper.toResponse(saved);
    }


    public Page<CasesResponse> findAll(CasesFilter filter, Pageable pageable) {
        Specification<Cases> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("observation", filter.observation()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.entityName()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
