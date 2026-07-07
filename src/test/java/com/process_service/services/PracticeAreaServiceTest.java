package com.process_service.services;

import com.process_service.dto.PracticeArea.*;
import com.process_service.entity.PracticeAreas;
import com.process_service.repository.PracticeAreaRepository;
import com.process_service.mapper.PracticeAreaMapper;
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
class PracticeAreaServiceTest {

    @Mock
    private PracticeAreaRepository repository;

    @Mock
    private PracticeAreaMapper mapper;

    @InjectMocks
    private PracticeAreaService service;

    @Test
    public void practiceAreaService_createPracticeAreaService_ReturnsPracticeAreaDTO() {

        PracticeAreaDTO dto = PracticeAreaDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-practice-area")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        PracticeAreas entity = PracticeAreas.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        PracticeAreaResponse response = PracticeAreaResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(PracticeAreas.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        PracticeAreaResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenPracticeAreaExists_DeletePracticeArea() {
        UUID id = UUID.randomUUID();

        PracticeAreas practiceArea = PracticeAreas.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(practiceArea));

        service.delete(id);

        assertNotNull(practiceArea.getDeletedAt());

        verify(repository).save(practiceArea);
    }

    @Test
    public void deleteById_WhenPracticeAreaNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenPracticeAreaExists_FindPracticeArea() {
        UUID id = UUID.randomUUID();

        PracticeAreas practiceArea = PracticeAreas.builder()
                .id(id)
                .name("testing")
                .build();

        PracticeAreaResponse response = PracticeAreaResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(practiceArea));
        when(mapper.toResponse(practiceArea)).thenReturn(response);

        PracticeAreaResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenPracticeAreaNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenPracticeAreaExists_UpdatePracticeArea() {
        UUID id = UUID.randomUUID();

        UpdatePracticeAreaRequest updated = UpdatePracticeAreaRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        PracticeAreas practiceArea = PracticeAreas.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        PracticeAreas saved = PracticeAreas.builder()
                .id(id)
                .name("testing")
                .build();

        PracticeAreaResponse response = PracticeAreaResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(practiceArea));
        doNothing().when(mapper).UpdateEntityFromDto(updated, practiceArea);
        when(repository.save(practiceArea)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        PracticeAreaResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(practiceArea.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, practiceArea);
        verify(repository).save(practiceArea);
    }

    @Test
    public void updateById_WhenPracticeAreaNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdatePracticeAreaRequest updated = UpdatePracticeAreaRequest.builder()
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
    void findAll_WhenPracticeAreaExists_FindAllPracticeAreas() {

        PracticeAreaFilter filter = new PracticeAreaFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        PracticeAreas practiceArea = PracticeAreas.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        PracticeAreaResponse response = PracticeAreaResponse.builder()
                .name("testing")
                .build();

        Page<PracticeAreas> page = new PageImpl<>(List.of(practiceArea));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(practiceArea)).thenReturn(response);

        Page<PracticeAreaResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenPracticeAreaNotExists_ReturnsEmptyPage() {
        PracticeAreaFilter filter = new PracticeAreaFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<PracticeAreas> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<PracticeAreaResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
