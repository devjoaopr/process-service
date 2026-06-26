package com.process_service.services;

import com.process_service.dto.ProcessFilter;
import com.process_service.dto.ProcessResponse;
import com.process_service.dto.ProcessDTO;
import com.process_service.dto.UpdateProcessRequest;

import com.process_service.entity.Process;
import com.process_service.shared.ResourceNotFoundException;
import com.process_service.mapper.ProcessMapper;
import com.process_service.repository.ProcessRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
public class ProcessService {

    @Autowired
    ProcessMapper processMapper;
    @Autowired
    ProcessRepository repository;

    public ProcessResponse createProcess(ProcessDTO processDTO) {

        Process process = processMapper.toEntity(processDTO);

        process.setId(UUID.randomUUID());
        process.setCreatedAt(OffsetDateTime.now());

        Process saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Process process = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado"));

        repository.delete(process);

    }

    public ProcessResponse findById(UUID id) {
        return processMapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado")));
    }

    public ProcessResponse updateById(UUID id, UpdateProcessRequest request) {
        Process process = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        processMapper.updateEntityFromDto(request, process);

        Process saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    private Specification<Process> likeFilter(String field, String value) {
        if (value == null || value.isBlank()) return Specification.unrestricted();
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private Specification<Process> equalFilter(String field, Object value) {
        if (value == null) return Specification.unrestricted();
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private Specification<Process> dateFilter(String field, OffsetDateTime value) {
        if (value == null) return Specification.unrestricted();
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), value);
    }

    private <T> Specification<Process> inFilter(String field, List<T> values) {
        if (values == null || values.isEmpty()) return Specification.unrestricted();
        return (root, query, cb) -> root.get(field).in(values);
    }

    //quickdraw !
    public Page<ProcessResponse> findAll(ProcessFilter filter, Pageable pageable) {
        Specification<Process> spec = Specification.unrestricted();

        spec = spec
                .and(likeFilter("cnjNumber", filter.cnjNumber()))
                .and(likeFilter("oldProcessNumber", filter.oldProcessNumber()))
                .and(likeFilter("folderNumber", filter.folderNumber()))
                .and(likeFilter("stateUf", filter.stateUf()))
                .and(likeFilter("requestText", filter.requestText()))
                .and(likeFilter("observation", filter.observation()))
                .and(equalFilter("internalCode", filter.internalCode()))
                .and(equalFilter("responsibleUserId", filter.responsibleUserId()))
                .and(equalFilter("isFavorite", filter.isFavorite()))
                .and(equalFilter("justiceSecret", filter.justiceSecret()))
                .and(equalFilter("captureMovements", filter.captureMovements()))
                .and(dateFilter("createdAt", filter.createdAt()))
                .and(dateFilter("updatedAt", filter.updatedAt()))
                .and(dateFilter("deletedAt", filter.deletedAt()))
                .and(inFilter("situationOptionId", filter.situationOptionId()))
                .and(inFilter("processTypeOptionId", filter.processTypeOptionId()))
                .and(inFilter("groupOptionId", filter.groupOptionId()))
                .and(inFilter("practiceAreaOptionId", filter.practiceAreaOptionId()))
                .and(inFilter("actionObjectOptionId", filter.actionObjectOptionId()))
                .and(inFilter("subjectOptionId", filter.subjectOptionId()))
                .and(inFilter("detailOptionId", filter.detailOptionId()))
                .and(inFilter("prognosisOptionId", filter.prognosisOptionId()))
                .and(inFilter("conferenceOptionId", filter.conferenceOptionId()))
                .and(inFilter("partnerOptionId", filter.partnerOptionId()))
                .and(inFilter("originOptionId", filter.originOptionId()))
                .and(inFilter("phaseOptionId", filter.phaseOptionId()))
                .and(inFilter("locatorOptionId", filter.locatorOptionId()))
                .and(inFilter("courtUnitId", filter.courtUnitId()))
                .and(inFilter("countyId", filter.countyId()))
                .and(inFilter("createdById", filter.createdById()))
                .and(inFilter("updatedById", filter.updatedById()));

        return repository.findAll(spec, pageable)
                .map(processMapper::toResponse);
    }

}
