package com.process_service.services;

import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictFilter;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.District;
import com.process_service.mapper.DistrictMapper;
import com.process_service.repository.DistrictRepository;
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
public class DistrictService {

    @Autowired
    DistrictRepository repository;
    @Autowired
    DistrictMapper mapper;


    public DistrictResponse createDistrict(DistrictDTO districtDTO) {

        District district = mapper.toEntity(districtDTO);

        district.setId(UUID.randomUUID());
        district.setCreatedAt(OffsetDateTime.now());

        District saved = repository.save(district);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        District district = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        district.setDeletedAt(OffsetDateTime.now());
        repository.delete(district);

    }

    public DistrictResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public DistrictResponse updateById(UUID id, UpdateDistrictRequest request) {
        District district = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, district);
        district.setUpdatedAt(OffsetDateTime.now());
        District saved = repository.save(district);

        return mapper.toResponse(saved);
    }

    public Page<DistrictResponse> findAll(DistrictFilter filter, Pageable pageable) {
        Specification<District> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("cnjId", filter.cnjId()))
                .and(SpecificationUtils.in("internalId", filter.internalId()))
                .and(SpecificationUtils.in("state", filter.state()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.in("tjId", filter.tjId()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
