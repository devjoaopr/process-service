package com.process_service.services;


import com.process_service.dto.ProcessType.ProcessTypeDTO;
import com.process_service.dto.ProcessType.ProcessTypeFilter;
import com.process_service.dto.ProcessType.ProcessTypeResponse;
import com.process_service.dto.ProcessType.UpdateProcessTypeRequest;

import com.process_service.entity.ProcessType;
import com.process_service.mapper.ProcessTypeMapper;
import com.process_service.repository.ProcessTypeRepository;
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
public class ProcessTypeService {
    @Autowired
    ProcessTypeRepository repository;
    @Autowired
    ProcessTypeMapper mapper;


    public ProcessTypeResponse create(ProcessTypeDTO processTypeDTO) {

        ProcessType processType = mapper.toEntity(processTypeDTO);

        processType.setId(UUID.randomUUID());
        processType.setCreatedAt(OffsetDateTime.now());

        ProcessType saved = repository.save(processType);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        ProcessType processType = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        processType.setDeletedAt(OffsetDateTime.now());
        repository.save(processType);

    }

    public ProcessTypeResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public ProcessTypeResponse updateById(UUID id, UpdateProcessTypeRequest request) {
        ProcessType processType = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, processType);
        processType.setUpdatedAt(OffsetDateTime.now());
        ProcessType saved = repository.save(processType);

        return mapper.toResponse(saved);
    }

    public Page<ProcessTypeResponse> findAll(ProcessTypeFilter filter, Pageable pageable) {
        Specification<ProcessType> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
