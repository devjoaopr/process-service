package com.process_service.services;

import com.process_service.dto.Origin.OriginFilter;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.ProcessSituation.ProcessSituationDTO;
import com.process_service.dto.ProcessSituation.ProcessSituationFilter;
import com.process_service.dto.ProcessSituation.ProcessSituationResponse;
import com.process_service.dto.ProcessSituation.UpdateProcessSituationRequest;
import com.process_service.entity.Origin;
import com.process_service.entity.ProcessSituation;
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
import java.util.List;
import java.util.UUID;

@Service
public class ProcessSituationService {

    @Autowired
    ProcessSituationRepository repository;

    @Autowired
    ProcessSituationMapper processMapper;

    public ProcessSituationResponse createProcessSituation(ProcessSituationDTO processSituationDTO) {

        ProcessSituation process = processMapper.toEntity(processSituationDTO);

        process.setId(UUID.randomUUID());
        process.setCreatedAt(OffsetDateTime.now());

        ProcessSituation saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        ProcessSituation process = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado"));
        process.setDeletedAt(OffsetDateTime.now());
        repository.save(process);

    }

    public ProcessSituationResponse findById(UUID id) {
        return processMapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado")));
    }

    public ProcessSituationResponse updateById(UUID id, UpdateProcessSituationRequest request) {
        ProcessSituation process = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        processMapper.updateEntityFromDto(request, process);
        process.setUpdatedAt(OffsetDateTime.now());
        ProcessSituation saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public Page<ProcessSituationResponse> findAll(ProcessSituationFilter filter, Pageable pageable) {
        Specification<ProcessSituation> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(processMapper::toResponse);
    }
}
