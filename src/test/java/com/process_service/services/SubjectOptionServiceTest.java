package com.process_service.services;

import com.process_service.dto.SubjectOption.*;
import com.process_service.entity.SubjectOptions;
import com.process_service.mapper.SubjectOptionMapper;
import com.process_service.repository.SubjectOptionRepository;
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
class SubjectOptionServiceTest {

    @Mock
    private SubjectOptionRepository repository;

    @Mock
    private SubjectOptionMapper mapper;

    @InjectMocks
    private SubjectOptionService service;

    @Test
    public void subjectOptionService_createSubjectOptionService_ReturnsSubjectOptionDTO() {

        SubjectOptionDTO dto = SubjectOptionDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-subject-option")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        SubjectOptions entity = SubjectOptions.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        SubjectOptionResponse response = SubjectOptionResponse.builder()
                .name("testing")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(SubjectOptions.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        SubjectOptionResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenSubjectOptionExists_DeleteSubjectOption() {
        UUID id = UUID.randomUUID();

        SubjectOptions subjectOption = SubjectOptions.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(subjectOption));

        service.delete(id);

        assertNotNull(subjectOption.getDeletedAt());

        verify(repository).save(subjectOption);
    }

    @Test
    public void deleteById_WhenSubjectOptionNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenSubjectOptionExists_FindSubjectOption() {
        UUID id = UUID.randomUUID();

        SubjectOptions subjectOption = SubjectOptions.builder()
                .id(id)
                .name("testing")
                .build();

        SubjectOptionResponse response = SubjectOptionResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(subjectOption));
        when(mapper.toResponse(subjectOption)).thenReturn(response);

        SubjectOptionResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void findById_WhenSubjectOptionNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenSubjectOptionExists_UpdateSubjectOption() {
        UUID id = UUID.randomUUID();

        UpdateSubjectOptionRequest updated = UpdateSubjectOptionRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        SubjectOptions subjectOption = SubjectOptions.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        SubjectOptions saved = SubjectOptions.builder()
                .id(id)
                .name("testing")
                .build();

        SubjectOptionResponse response = SubjectOptionResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(subjectOption));
        doNothing().when(mapper).UpdateEntityFromDto(updated, subjectOption);
        when(repository.save(subjectOption)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        SubjectOptionResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing-name", result.name());
        assertNotNull(subjectOption.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, subjectOption);
        verify(repository).save(subjectOption);
    }

    @Test
    public void updateById_WhenSubjectOptionNotExists_ThrowsResourceNotFoundException() {
        UUID id = UUID.randomUUID();

        UpdateSubjectOptionRequest updated = UpdateSubjectOptionRequest.builder()
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
    void findAll_WhenSubjectOptionExists_FindAllSubjectOptions() {

        SubjectOptionFilter filter = new SubjectOptionFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        SubjectOptions subjectOption = SubjectOptions.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        SubjectOptionResponse response = SubjectOptionResponse.builder()
                .name("testing")
                .build();

        Page<SubjectOptions> page = new PageImpl<>(List.of(subjectOption));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(subjectOption)).thenReturn(response);

        Page<SubjectOptionResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenSubjectOptionNotExists_ReturnsEmptyPage() {
        SubjectOptionFilter filter = new SubjectOptionFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<SubjectOptions> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<SubjectOptionResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}