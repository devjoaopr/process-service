package com.process_service.services;


import com.process_service.dto.ProcessSituation.*;

import com.process_service.entity.ProcessSituations;
import com.process_service.mapper.ProcessSituationMapper;
import com.process_service.repository.ProcessSituationRepository;
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
class ProcessSituationServiceTest {

    @Mock
    private ProcessSituationRepository repository;

    @Mock
    private ProcessSituationMapper mapper;

    @InjectMocks
    private ProcessSituationService service;

    @Test
    public void processSituationService_createProcessSituationService_ReturnsProcessSituationDTO() {

        ProcessSituationDTO dto = ProcessSituationDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-process-situation")
                .slug("testing-slug")
                .active(true)
                .createdAt(OffsetDateTime.now())
                .build();

        ProcessSituations entity = ProcessSituations.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .active(true)
                .createdAt(OffsetDateTime.now())
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(ProcessSituations.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        ProcessSituationResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenProcessSituationExists_DeleteProcessSituation() {
        UUID id = UUID.randomUUID();

        ProcessSituations processSituation = ProcessSituations.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processSituation));

        service.delete(id);

        assertNotNull(processSituation.getDeletedAt());

        verify(repository).save(processSituation);
    }

    @Test
    public void deleteById_WhenProcessSituationNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenProcessSituationExists_FindProcessSituation() {
        UUID id = UUID.randomUUID();

        ProcessSituations processSituation = ProcessSituations.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processSituation));
        when(mapper.toResponse(processSituation)).thenReturn(response);

        ProcessSituationResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenProcessSituationNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenProcessSituationExists_UpdateProcessSituation() {
        UUID id = UUID.randomUUID();

        UpdateProcessSituationRequest updated = UpdateProcessSituationRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .active(true)
                .updatedAt(OffsetDateTime.now())
                .build();

        ProcessSituations processSituation = ProcessSituations.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .active(true)
                .updatedAt(OffsetDateTime.now())
                .build();

        ProcessSituations saved = ProcessSituations.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(processSituation));
        doNothing().when(mapper).updateEntityFromDto(updated, processSituation);
        when(repository.save(processSituation)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ProcessSituationResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(processSituation.getUpdatedAt());
        verify(mapper).updateEntityFromDto(updated, processSituation);
        verify(repository).save(processSituation);
    }

    @Test
    public void updateById_WhenProcessSituationNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdateProcessSituationRequest updated = UpdateProcessSituationRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, updated));
        verify(repository, never()).save(any());
        verify(mapper, never()).updateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenProcessSituationExists_FindAllProcessSituations() {

        ProcessSituationFilter filter = new ProcessSituationFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        ProcessSituations processSituation = ProcessSituations.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .name("testing")
                .build();

        Page<ProcessSituations> page = new PageImpl<>(List.of(processSituation));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(processSituation)).thenReturn(response);

        Page<ProcessSituationResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenProcessSituationNotExists_ReturnsEmptyPage() {
        ProcessSituationFilter filter = new ProcessSituationFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProcessSituations> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<ProcessSituationResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
