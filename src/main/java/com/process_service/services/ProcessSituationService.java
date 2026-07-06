package com.process_service.services;

import com.process_service.dto.ProcessSituation.ProcessSituationDTO;
import com.process_service.dto.ProcessSituation.ProcessSituationFilter;
import com.process_service.dto.ProcessSituation.ProcessSituationResponse;
import com.process_service.dto.ProcessSituation.UpdateProcessSituationRequest;

import com.process_service.entity.ProcessSituations;
import com.process_service.shared.ResourceNotFoundException;
import com.process_service.mapper.ProcessSituationMapper;
import com.process_service.repository.ProcessSituationRepository;
import com.process_service.shared.SpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ProcessSituationService {

    @Autowired
    ProcessSituationRepository repository;

    @Autowired
    ProcessSituationMapper processMapper;

    public ProcessSituationResponse create(ProcessSituationDTO processSituationDTO) {

        ProcessSituations process = processMapper.toEntity(processSituationDTO);
        return processMapper.toResponse(repository.save(process));
    }

    public void delete(UUID id) {
        ProcessSituations process = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado"));
        process.setDeletedAt(OffsetDateTime.now());
        repository.save(process);

    }

    public ProcessSituationResponse get(UUID id) {
        return processMapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado")));
    }

    public ProcessSituationResponse update(UUID id, UpdateProcessSituationRequest request) {
        ProcessSituations process = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        processMapper.updateEntityFromDto(request, process);
        process.setUpdatedAt(OffsetDateTime.now());
        ProcessSituations saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public Page<ProcessSituationResponse> findAll(ProcessSituationFilter filter, Pageable pageable) {
        Specification<ProcessSituations> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(processMapper::toResponse);
    }
}
