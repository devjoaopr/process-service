package com.process_service.services;

import com.process_service.dto.*;
import com.process_service.entity.Process;
import com.process_service.entity.ProcessSituation;
import com.process_service.handlers.ResourceNotFoundException;
import com.process_service.mapper.ProcessMapper;
import com.process_service.mapper.ProcessSituationMapper;
import com.process_service.repository.ProcessSituationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        repository.delete(process);

    }

    public ProcessSituationResponse findById(UUID id) {
        return processMapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado")));
    }

    public ProcessSituationResponse updateById(UUID id, UpdateProcessSituationRequest request) {
        ProcessSituation process = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        processMapper.updateEntityFromDto(request, process);

        ProcessSituation saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public List<ProcessSituationResponse> filterByInput(ProcessSituationFilter filter) {
        Specification<ProcessSituation> spec = Specification.unrestricted();

        if (filter.description() != null && !filter.description().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("description")),
                            "%" + filter.description().toLowerCase() + "%"
                    )
            );
        }

        if (filter.name() != null && !filter.name().isBlank()) {

            spec = spec.and((root, query, cb) ->
                    cb.like(
                            cb.lower(root.get("name")),
                            "%" + filter.name().toLowerCase() + "%"
                    )
            );
        }

        if (filter.active() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("active"), filter.active())
            );
        }

        if (filter.slug() != null && !filter.slug().isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("slug")), "%" + filter.slug().toLowerCase() + "%"));
        }


        return repository.findAll(spec)
                .stream()
                .map(processMapper::toResponse)
                .toList();
    }

}
