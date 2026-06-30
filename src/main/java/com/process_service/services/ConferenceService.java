package com.process_service.services;

import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceFilter;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.entity.Conference;
import com.process_service.mapper.ConferenceMapper;
import com.process_service.repository.ConferenceRepository;
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
public class ConferenceService {

    @Autowired
    private ConferenceRepository repository;
    @Autowired
    private ConferenceMapper mapper;

    public ConferenceResponse create(ConferenceDTO conferenceDTO) {

        Conference conference = mapper.toEntity(conferenceDTO);

        conference.setId(UUID.randomUUID());
        conference.setCreatedAt(OffsetDateTime.now());

        Conference saved = repository.save(conference);

        return mapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Conference conference = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        conference.setDeletedAt(OffsetDateTime.now());
        repository.save(conference);

    }

    public ConferenceResponse findById(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public ConferenceResponse updateById(UUID id, UpdateConferenceRequest request) {
        Conference conference = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, conference);
        conference.setUpdatedAt(OffsetDateTime.now());
        Conference saved = repository.save(conference);

        return mapper.toResponse(saved);
    }


    public Page<ConferenceResponse> findAll(ConferenceFilter filter, Pageable pageable) {
        Specification<Conference> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("description", filter.description()))
                .and(SpecificationUtils.in("name", filter.name()))
                .and(SpecificationUtils.in("slug", filter.slug()))
                .and(SpecificationUtils.equal("active", filter.active()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }


}
