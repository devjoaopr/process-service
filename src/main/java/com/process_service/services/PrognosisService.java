package com.process_service.services;

import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailFilter;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.dto.Prognosis.PrognosisDTO;
import com.process_service.dto.Prognosis.PrognosisFilter;
import com.process_service.dto.Prognosis.PrognosisResponse;
import com.process_service.dto.Prognosis.UpdatePrognosisRequest;
import com.process_service.entity.Detail;
import com.process_service.entity.Prognosis;
import com.process_service.mapper.PrognosisMapper;
import com.process_service.repository.PrognosisRepository;
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
public class PrognosisService {
    @Autowired
    private PrognosisRepository repository;
    @Autowired
    private PrognosisMapper mapper;

    public PrognosisResponse create(PrognosisDTO dto) {

        Prognosis prognosis = mapper.toEntity(dto);

        prognosis.setId(UUID.randomUUID());
        prognosis.setCreatedAt(OffsetDateTime.now());

        Prognosis saved = repository.save(prognosis);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Prognosis prognosis = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        prognosis.setDeletedAt(OffsetDateTime.now());
        repository.save(prognosis);
    }

    public PrognosisResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public PrognosisResponse updateById(UUID id, UpdatePrognosisRequest request) {
        Prognosis prognosis = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, prognosis);
        prognosis.setUpdatedAt(OffsetDateTime.now());
        Prognosis saved = repository.save(prognosis);

        return mapper.toResponse(saved);
    }


    public Page<PrognosisResponse> findAll(PrognosisFilter filter, Pageable pageable) {
        Specification<Prognosis> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}
