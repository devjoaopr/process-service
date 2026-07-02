package com.process_service.services;
import com.process_service.dto.Origin.*;
import com.process_service.entity.Origin;
import com.process_service.repository.OriginRepository;
import com.process_service.mapper.OriginMapper;
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
class OriginServiceTest {

    @Mock
    private OriginRepository repository;

    @Mock
    private OriginMapper mapper;

    @InjectMocks
    private OriginService service;

    @Test
    public void originService_createOriginService_ReturnsOriginDTO() {

        OriginDTO dto = OriginDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-origin")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        Origin entity = Origin.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        OriginResponse response = OriginResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Origin.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        OriginResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenOriginExists_DeleteOrigin() {
        UUID id = UUID.randomUUID();

        Origin origin = Origin.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(origin));

        service.deleteById(id);

        assertNotNull(origin.getDeletedAt());

        verify(repository).save(origin);
    }

    @Test
    public void deleteById_WhenOriginNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenOriginExists_FindOrigin() {
        UUID id = UUID.randomUUID();

        Origin origin = Origin.builder()
                .id(id)
                .name("testing")
                .build();

        OriginResponse response = OriginResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(origin));
        when(mapper.toResponse(origin)).thenReturn(response);

        OriginResponse result = service.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenOriginNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenOriginExists_UpdateOrigin() {
        UUID id = UUID.randomUUID();

        UpdateOriginRequest updated = UpdateOriginRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Origin origin = Origin.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Origin saved = Origin.builder()
                .id(id)
                .name("testing")
                .build();

        OriginResponse response = OriginResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(origin));
        doNothing().when(mapper).UpdateEntityFromDto(updated, origin);
        when(repository.save(origin)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        OriginResponse result = service.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(origin.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, origin);
        verify(repository).save(origin);
    }

    @Test
    public void updateById_WhenOriginNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdateOriginRequest updated = UpdateOriginRequest.builder()
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
    void findAll_WhenOriginExists_FindAllOrigins() {

        OriginFilter filter = new OriginFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Origin origin = Origin.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        OriginResponse response = OriginResponse.builder()
                .name("testing")
                .build();

        Page<Origin> page = new PageImpl<>(List.of(origin));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(origin)).thenReturn(response);

        Page<OriginResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenOriginNotExists_ReturnsEmptyPage() {
        OriginFilter filter = new OriginFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Origin> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<OriginResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
