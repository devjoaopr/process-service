package com.process_service.services;

import com.process_service.dto.Cases.CasesDTO;
import com.process_service.dto.Cases.CasesFilter;
import com.process_service.dto.Cases.CasesResponse;
import com.process_service.dto.Cases.UpdateCasesRequest;
import com.process_service.dto.Tasks.TaskDTO;
import com.process_service.dto.Tasks.TaskFilter;
import com.process_service.dto.Tasks.TaskResponse;
import com.process_service.dto.Tasks.UpdateTaskRequest;
import com.process_service.entity.Cases;
import com.process_service.entity.Tasks;
import com.process_service.mapper.CasesMapper;
import com.process_service.mapper.TasksMapper;
import com.process_service.repository.CasesRepository;
import com.process_service.repository.TasksRepository;
import com.process_service.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.config.Task;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TasksRepository repository;

    @Mock
    private TasksMapper mapper;

    @InjectMocks
    private TaskService service;

    @Test
    public void TasksService_createTaskService_ReturnsTasksDTO() {

        TaskDTO dto = TaskDTO.builder()
                .entityNames("testing")
                .observation("what else?")
                .title("testinnnnggggg")
                .build();

        Tasks entity = Tasks.builder()
                .id(UUID.randomUUID())
                .title("testing-name")
                .observation("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        TaskResponse response = TaskResponse.builder()
                .entityNames("test-entity-name")
                .title("test-name")
                .observation("test-observation")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Tasks.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        TaskResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.title());
    }

    @Test
    public void deleteById_WhenTasksExists_DeleteTasks() {
        UUID id = UUID.randomUUID();

        Tasks tasks = Tasks.builder()
                .id(id)
                .title("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(tasks));

        service.delete(id);

        assertNotNull(tasks.getDeletedAt());

        verify(repository).save(tasks);
    }

    @Test
    public void deleteById_WhenTasksNotExists_DeleteTasks() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenTasksExists_FindTasks() {
        UUID id = UUID.randomUUID();

        Tasks tasks = Tasks.builder()
                .id(id)
                .title("testing")
                .build();

        TaskResponse response = TaskResponse.builder()
                .title("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(tasks));
        when(mapper.toResponse(tasks)).thenReturn(response);

        TaskResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.title());

    }

    @Test
    public void findById_WhenCasesNotExists_FindTasks() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenTasksExists_Tasks() {
        UUID id = UUID.randomUUID();

        UpdateTaskRequest updated = UpdateTaskRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .title("testing")
                .observation("test")
                .build();

        Tasks tasks = Tasks.builder()
                .id(id)
                .title("old testing")
                .build();

        Tasks saved = Tasks.builder()
                .id(id)
                .title("testing")
                .build();

        TaskResponse response = TaskResponse.builder()
                .title("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(tasks));
        doNothing().when(mapper).UpdateEntityFromDto(updated, tasks);
        when(repository.save(tasks)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        TaskResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.title());
        assertNotNull(tasks.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, tasks);
        verify(repository).save(tasks);
    }

    @Test
    public void updateById_WhenTasksNotExists_UpdateTasks() {
        UUID id = UUID.randomUUID();

        UpdateTaskRequest updated = UpdateTaskRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .title("testing")
                .observation("test")
                .build();


        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, updated));
        verify(repository, never()).save(any());
        verify(mapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenTaskExists_FindAllTasks() {

        TaskFilter filter = new TaskFilter(
                List.of("some description"),
                List.of("testing"),
                List.of("test-slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Tasks tasks = Tasks.builder()
                .id(UUID.randomUUID())
                .title("testing")
                .build();

        TaskResponse response = TaskResponse.builder()
                .title("testing")
                .build();

        Page<Tasks> page = new PageImpl<>(List.of(tasks));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(tasks)).thenReturn(response);

        Page<TaskResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().title());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenCasesNotExists_FindAllCases() {
        TaskFilter filter = new TaskFilter(
                null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Tasks> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<TaskResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
