package com.process_service.services;

import com.process_service.dto.PracticeArea.PracticeAreaDTO;
import com.process_service.dto.PracticeArea.PracticeAreaFilter;
import com.process_service.dto.PracticeArea.PracticeAreaResponse;
import com.process_service.dto.PracticeArea.UpdatePracticeAreaRequest;
import com.process_service.entity.PracticeAreas;
import com.process_service.mapper.PracticeAreaMapper;
import com.process_service.repository.PracticeAreaRepository;
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
public class PracticeAreaService {

    @Autowired
    PracticeAreaRepository repository;
    @Autowired
    PracticeAreaMapper mapper;


    public PracticeAreaResponse create(PracticeAreaDTO practiceAreaDTO) {

        PracticeAreas practiceArea = mapper.toEntity(practiceAreaDTO);
        return mapper.toResponse(repository.save(practiceArea));
    }

    public void delete(UUID id) {
        PracticeAreas practiceArea = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        practiceArea.setDeletedAt(OffsetDateTime.now());
        repository.save(practiceArea);

    }

    public PracticeAreaResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public PracticeAreaResponse update(UUID id, UpdatePracticeAreaRequest request) {
        PracticeAreas practiceArea = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, practiceArea);
        practiceArea.setUpdatedAt(OffsetDateTime.now());
        PracticeAreas saved = repository.save(practiceArea);

        return mapper.toResponse(saved);
    }

    public Page<PracticeAreaResponse> findAll(PracticeAreaFilter filter, Pageable pageable) {
        Specification<PracticeAreas> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
