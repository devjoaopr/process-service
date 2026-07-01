package com.process_service.services;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectFilter;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictFilter;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.ActionObject;
import com.process_service.entity.District;
import com.process_service.mapper.ActionObjectMapper;
import com.process_service.mapper.DistrictMapper;
import com.process_service.repository.ActionObjectRepository;
import com.process_service.repository.DistrictRepository;
import com.process_service.shared.ResourceNotFoundException;
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
public class ActionObjectService {

    @Autowired
    ActionObjectRepository repository;
    @Autowired
    ActionObjectMapper mapper;


    public ActionObjectResponse createActionObject(ActionObjectDTO actionObjectDTO) {

        ActionObject action = mapper.toEntity(actionObjectDTO);

        action.setId(UUID.randomUUID());
        action.setCreatedAt(OffsetDateTime.now());

        ActionObject saved = repository.save(action);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        ActionObject action = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        action.setDeletedAt(OffsetDateTime.now());
        repository.delete(action);

    }

    public ActionObjectResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public ActionObjectResponse updateById(UUID id, UpdateActionObjectRequest request) {
        ActionObject actionObject = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, actionObject);
        actionObject.setUpdatedAt(OffsetDateTime.now());
        ActionObject saved = repository.save(actionObject);

        return mapper.toResponse(saved);
    }

    public Page<ActionObjectResponse> findAll(ActionObjectFilter filter, Pageable pageable) {
        Specification<ActionObject> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.equal("active", filter.active()))
                .and(SpecificationUtils.in("description", filter.description()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
