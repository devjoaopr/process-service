package com.process_service.services;

import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseFilter;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.entity.Phases;
import com.process_service.mapper.PhaseMapper;
import com.process_service.repository.PhaseRepository;
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
public class PhaseService {

    @Autowired
    PhaseRepository repository;
    @Autowired
    PhaseMapper mapper;


    public PhaseResponse create(PhaseDTO phaseDTO) {
        Phases phase = mapper.toEntity(phaseDTO);
        return mapper.toResponse(repository.save(phase));
    }

    public void delete(UUID id) {
        Phases phase = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        phase.setDeletedAt(OffsetDateTime.now());
        repository.save(phase);

    }

    public PhaseResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public PhaseResponse update(UUID id, UpdatePhaseRequest request) {
        Phases phase = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, phase);
        phase.setUpdatedAt(OffsetDateTime.now());
        Phases saved = repository.save(phase);

        return mapper.toResponse(saved);
    }

    public Page<PhaseResponse> findAll(PhaseFilter filter, Pageable pageable) {
        Specification<Phases> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
