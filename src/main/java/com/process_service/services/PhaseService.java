package com.process_service.services;

import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginFilter;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseFilter;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.entity.Origin;
import com.process_service.entity.Phase;
import com.process_service.mapper.OriginMapper;
import com.process_service.mapper.PhaseMapper;
import com.process_service.repository.OriginRepository;
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

        Phase phase = mapper.toEntity(phaseDTO);

        phase.setId(UUID.randomUUID());
        phase.setCreatedAt(OffsetDateTime.now());

        Phase saved = repository.save(phase);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Phase phase = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        phase.setDeletedAt(OffsetDateTime.now());
        repository.save(phase);

    }

    public PhaseResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public PhaseResponse updateById(UUID id, UpdatePhaseRequest request) {
        Phase phase = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, phase);
        phase.setUpdatedAt(OffsetDateTime.now());
        Phase saved = repository.save(phase);

        return mapper.toResponse(saved);
    }

    public Page<PhaseResponse> findAll(PhaseFilter filter, Pageable pageable) {
        Specification<Phase> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
