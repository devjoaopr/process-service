package com.process_service.services;

import com.process_service.dto.Prognosis.PrognosisDTO;
import com.process_service.dto.Prognosis.PrognosisFilter;
import com.process_service.dto.Prognosis.PrognosisResponse;
import com.process_service.dto.Prognosis.UpdatePrognosisRequest;
import com.process_service.entity.Prognosis;
import com.process_service.mapper.PrognosisMapper;
import com.process_service.repository.PrognosisRepository;
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
class PrognosisServiceTest {

    @Mock
    private PrognosisRepository repository;

    @Mock
    private PrognosisMapper mapper;

    @InjectMocks
    private PrognosisService service;

    @Test
    public void prognosisService_createPrognosisService_ReturnsPrognosisDTO() {

        PrognosisDTO dto = PrognosisDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-prognosis")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        Prognosis entity = Prognosis.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        PrognosisResponse response = PrognosisResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Prognosis.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PrognosisResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenPrognosisExists_DeletePrognosis() {
        UUID id = UUID.randomUUID();

        Prognosis prognosis = Prognosis.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(prognosis));

        service.delete(id);

        assertNotNull(prognosis.getDeletedAt());

        verify(repository).save(prognosis);
    }

    @Test
    public void deleteById_WhenPrognosisNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenPrognosisExists_FindPrognosis() {
        UUID id = UUID.randomUUID();

        Prognosis prognosis = Prognosis.builder()
                .id(id)
                .name("testing")
                .build();

        PrognosisResponse response = PrognosisResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(prognosis));
        when(mapper.toResponse(prognosis)).thenReturn(response);

        PrognosisResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenPrognosisNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenPrognosisExists_UpdatePrognosis() {
        UUID id = UUID.randomUUID();

        UpdatePrognosisRequest updated = UpdatePrognosisRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Prognosis prognosis = Prognosis.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Prognosis saved = Prognosis.builder()
                .id(id)
                .name("testing")
                .build();

        PrognosisResponse response = PrognosisResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(prognosis));
        doNothing().when(mapper).UpdateEntityFromDto(updated, prognosis);
        when(repository.save(prognosis)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        PrognosisResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(prognosis.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, prognosis);
        verify(repository).save(prognosis);
    }

    @Test
    public void updateById_WhenPrognosisNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdatePrognosisRequest updated = UpdatePrognosisRequest.builder()
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
    void findAll_WhenPrognosisExists_FindAllPrognosis() {

        PrognosisFilter filter = new PrognosisFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Prognosis prognosis = Prognosis.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        PrognosisResponse response = PrognosisResponse.builder()
                .name("testing")
                .build();

        Page<Prognosis> page = new PageImpl<>(List.of(prognosis));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(prognosis)).thenReturn(response);

        Page<PrognosisResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenPrognosisNotExists_ReturnsEmptyPage() {
        PrognosisFilter filter = new PrognosisFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Prognosis> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<PrognosisResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}