package com.process_service.services;
import com.process_service.dto.ProcessType.*;

import com.process_service.entity.ProcessType;
import com.process_service.mapper.ProcessTypeMapper;
import com.process_service.repository.ProcessTypeRepository;
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

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTypeServiceTest {

    @Mock
    private ProcessTypeRepository repository;

    @Mock
    private ProcessTypeMapper mapper;

    @InjectMocks
    private ProcessTypeService service;

    @Test
    public void processTypeService_createProcessTypeService_ReturnsProcessTypeDTO() {

        ProcessTypeDTO dto = ProcessTypeDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-process-type")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        ProcessType entity = ProcessType.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        ProcessTypeResponse response = ProcessTypeResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(ProcessType.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        ProcessTypeResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenProcessTypeExists_DeleteProcessType() {
        UUID id = UUID.randomUUID();

        ProcessType processType = ProcessType.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processType));

        service.deleteById(id);

        assertNotNull(processType.getDeletedAt());

        verify(repository).save(processType);
    }

    @Test
    public void deleteById_WhenProcessTypeNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenProcessTypeExists_FindProcessType() {
        UUID id = UUID.randomUUID();

        ProcessType processType = ProcessType.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessTypeResponse response = ProcessTypeResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processType));
        when(mapper.toResponse(processType)).thenReturn(response);

        ProcessTypeResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenProcessTypeNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenProcessTypeExists_UpdateProcessType() {
        UUID id = UUID.randomUUID();

        UpdateProcessTypeRequest updated = UpdateProcessTypeRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        ProcessType processType = ProcessType.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        ProcessType saved = ProcessType.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessTypeResponse response = ProcessTypeResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processType));
        doNothing().when(mapper).UpdateEntityFromDto(updated, processType);
        when(repository.save(processType)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ProcessTypeResponse result = service.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(processType.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, processType);
        verify(repository).save(processType);
    }

    @Test
    public void updateById_WhenProcessTypeNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdateProcessTypeRequest updated = UpdateProcessTypeRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.updateById(id, updated));
        verify(repository, never()).save(any());
        verify(mapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenProcessTypeExists_FindAllProcessTypes() {

        ProcessTypeFilter filter = new ProcessTypeFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        ProcessType processType = ProcessType.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        ProcessTypeResponse response = ProcessTypeResponse.builder()
                .name("testing")
                .build();

        Page<ProcessType> page = new PageImpl<>(List.of(processType));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(processType)).thenReturn(response);

        Page<ProcessTypeResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenProcessTypeNotExists_ReturnsEmptyPage() {
        ProcessTypeFilter filter = new ProcessTypeFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProcessType> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<ProcessTypeResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
