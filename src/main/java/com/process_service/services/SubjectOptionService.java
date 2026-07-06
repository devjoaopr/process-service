package com.process_service.services;

import com.process_service.dto.SubjectOption.SubjectOptionDTO;
import com.process_service.dto.SubjectOption.SubjectOptionFilter;
import com.process_service.dto.SubjectOption.SubjectOptionResponse;
import com.process_service.dto.SubjectOption.UpdateSubjectOptionRequest;

import com.process_service.entity.SubjectOptions;
import com.process_service.mapper.SubjectOptionMapper;
import com.process_service.repository.SubjectOptionRepository;
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
public class SubjectOptionService {
    @Autowired
    SubjectOptionRepository repository;
    @Autowired
    SubjectOptionMapper mapper;


    public SubjectOptionResponse create(SubjectOptionDTO subjectOptionDTO) {

        SubjectOptions subjectOption = mapper.toEntity(subjectOptionDTO);
        return mapper.toResponse(repository.save(subjectOption));
    }

    public void delete(UUID id) {
        SubjectOptions subjectOption = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        subjectOption.setDeletedAt(OffsetDateTime.now());
        repository.save(subjectOption);

    }

    public SubjectOptionResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public SubjectOptionResponse update(UUID id, UpdateSubjectOptionRequest request) {
        SubjectOptions subjectOption = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, subjectOption);
        subjectOption.setUpdatedAt(OffsetDateTime.now());
        SubjectOptions saved = repository.save(subjectOption);

        return mapper.toResponse(saved);
    }

    public Page<SubjectOptionResponse> findAll(SubjectOptionFilter filter, Pageable pageable) {
        Specification<SubjectOptions> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}

