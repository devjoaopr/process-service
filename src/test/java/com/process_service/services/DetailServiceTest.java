package com.process_service.services;
import com.process_service.dto.Detail.*;
import com.process_service.entity.Details;
import com.process_service.mapper.DetailMapper;
import com.process_service.repository.DetailRepository;
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
class DetailServiceTest {

    @Mock
    private DetailRepository repository;

    @Mock
    private DetailMapper mapper;

    @InjectMocks
    private DetailService service;

    @Test
    public void detailService_createDetailService_ReturnsDetailDTO() {

        DetailDTO dto = DetailDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-detail")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        Details entity = Details.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        DetailResponse response = DetailResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Details.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        DetailResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenDetailExists_DeleteDetail() {
        UUID id = UUID.randomUUID();

        Details detail = Details.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(detail));

        service.delete(id);

        assertNotNull(detail.getDeletedAt());

        verify(repository).save(detail);
    }

    @Test
    public void deleteById_WhenDetailNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenDetailExists_FindDetail() {
        UUID id = UUID.randomUUID();

        Details detail = Details.builder()
                .id(id)
                .name("testing")
                .build();

        DetailResponse response = DetailResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(detail));
        when(mapper.toResponse(detail)).thenReturn(response);

        DetailResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenDetailNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenDetailExists_UpdateDetail() {
        UUID id = UUID.randomUUID();

        UpdateDetailRequest updated = UpdateDetailRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Details detail = Details.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Details saved = Details.builder()
                .id(id)
                .name("testing")
                .build();

        DetailResponse response = DetailResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(detail));
        doNothing().when(mapper).UpdateEntityFromDto(updated, detail);
        when(repository.save(detail)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        DetailResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(detail.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, detail);
        verify(repository).save(detail);
    }

    @Test
    public void updateById_WhenDetailNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdateDetailRequest updated = UpdateDetailRequest.builder()
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
    void findAll_WhenDetailExists_FindAllDetails() {

        DetailFilter filter = new DetailFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Details detail = Details.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        DetailResponse response = DetailResponse.builder()
                .name("testing")
                .build();

        Page<Details> page = new PageImpl<>(List.of(detail));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(detail)).thenReturn(response);

        Page<DetailResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenDetailNotExists_ReturnsEmptyPage() {
        DetailFilter filter = new DetailFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Details> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<DetailResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
