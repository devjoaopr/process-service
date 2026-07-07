package com.process_service.services;

import com.process_service.dto.Phase.*;
import com.process_service.entity.Phases;
import com.process_service.repository.PhaseRepository;
import com.process_service.mapper.PhaseMapper;
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
class PhaseServiceTest {

    @Mock
    private PhaseRepository repository;

    @Mock
    private PhaseMapper mapper;

    @InjectMocks
    private PhaseService service;

    @Test
    public void phaseService_createPhaseService_ReturnsPhaseDTO() {

        PhaseDTO dto = PhaseDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-phase")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        Phases entity = Phases.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        PhaseResponse response = PhaseResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Phases.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PhaseResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenPhaseExists_DeletePhase() {
        UUID id = UUID.randomUUID();

        Phases phase = Phases.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(phase));

        service.delete(id);

        assertNotNull(phase.getDeletedAt());

        verify(repository).save(phase);
    }

    @Test
    public void deleteById_WhenPhaseNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenPhaseExists_FindPhase() {
        UUID id = UUID.randomUUID();

        Phases phase = Phases.builder()
                .id(id)
                .name("testing")
                .build();

        PhaseResponse response = PhaseResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(phase));
        when(mapper.toResponse(phase)).thenReturn(response);

        PhaseResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenPhaseNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenPhaseExists_UpdatePhase() {
        UUID id = UUID.randomUUID();

        UpdatePhaseRequest updated = UpdatePhaseRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Phases phase = Phases.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Phases saved = Phases.builder()
                .id(id)
                .name("testing")
                .build();

        PhaseResponse response = PhaseResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(phase));
        doNothing().when(mapper).UpdateEntityFromDto(updated, phase);
        when(repository.save(phase)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        PhaseResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(phase.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, phase);
        verify(repository).save(phase);
    }

    @Test
    public void updateById_WhenPhaseNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdatePhaseRequest updated = UpdatePhaseRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, updated));
        verify(repository, never()).save(any());
        verify(mapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenPhaseExists_FindAllPhases() {

        PhaseFilter filter = new PhaseFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Phases phase = Phases.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        PhaseResponse response = PhaseResponse.builder()
                .name("testing")
                .build();

        Page<Phases> page = new PageImpl<>(List.of(phase));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(phase)).thenReturn(response);

        Page<PhaseResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenPhaseNotExists_ReturnsEmptyPage() {
        PhaseFilter filter = new PhaseFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Phases> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<PhaseResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}