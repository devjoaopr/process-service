package com.process_service.services;

import com.process_service.dto.Tasks.TaskDTO;
import com.process_service.dto.Tasks.TaskFilter;
import com.process_service.dto.Tasks.TaskResponse;
import com.process_service.dto.Tasks.UpdateTaskRequest;
import com.process_service.entity.Tasks;
import com.process_service.mapper.TasksMapper;
import com.process_service.repository.TasksRepository;
import com.process_service.shared.ResourceNotFoundException;
import com.process_service.shared.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TaskService {

    private final TasksRepository repository;
    private final TasksMapper mapper;

    public TaskService(TasksRepository repository, TasksMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TaskResponse create(TaskDTO taskDTO) {

        Tasks tasks = mapper.toEntity(taskDTO);
        return mapper.toResponse(repository.save(tasks));
    }

    public void delete(UUID id) {
        Tasks tasks = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado"));
        tasks.setDeletedAt(OffsetDateTime.now());
        repository.save(tasks);
    }

    public TaskResponse get(UUID id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comarca nao encontrado")));
    }

    public TaskResponse update(UUID id, UpdateTaskRequest request) {
        Tasks tasks = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        mapper.UpdateEntityFromDto(request, tasks);
        tasks.setUpdatedAt(OffsetDateTime.now());
        Tasks saved = repository.save(tasks);

        return mapper.toResponse(saved);
    }

    public Page<TaskResponse> findAll(TaskFilter filter, Pageable pageable) {
        Specification<Tasks> spec = Specification.unrestricted();

        spec = spec
                .and(SpecificationUtils.in("entity-names", filter.entityNames()))
                .and(SpecificationUtils.in("observation", filter.observation()))
                .and(SpecificationUtils.in("title", filter.title()));

        return repository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

}
