package com.process_service.services;

import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailFilter;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.entity.Detail;
import com.process_service.mapper.DetailMapper;
import com.process_service.repository.DetailRepository;
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
public class DetailService {
    @Autowired
    private DetailRepository repository;
    @Autowired
    private DetailMapper mapper;

    public DetailResponse create(DetailDTO dto) {

        Detail detail = mapper.toEntity(dto);

        detail.setId(UUID.randomUUID());
        detail.setCreatedAt(OffsetDateTime.now());

        Detail saved = repository.save(detail);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Detail detail = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        detail.setDeletedAt(OffsetDateTime.now());
        repository.save(detail);

    }

    public DetailResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public DetailResponse updateById(UUID id, UpdateDetailRequest request) {
        Detail detail = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, detail);
        detail.setUpdatedAt(OffsetDateTime.now());
        Detail saved = repository.save(detail);

        return mapper.toResponse(saved);
    }


    public Page<DetailResponse> findAll(DetailFilter filter, Pageable pageable) {
        Specification<Detail> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
