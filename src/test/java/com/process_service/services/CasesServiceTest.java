package com.process_service.services;

import com.process_service.dto.Cases.*;
import com.process_service.entity.Cases;
import com.process_service.mapper.CasesMapper;
import com.process_service.repository.CasesRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CasesServiceTest {

    @Mock
    private CasesRepository repository;

    @Mock
    private CasesMapper mapper;

    @InjectMocks
    private CaseService service;

    @Test
    public void CasesService_createCasesService_ReturnsCasesDTO() {

        CasesDTO dto = CasesDTO.builder()
                .name("testing")
                .observation("what else?")
                .entityName("testinnnnggggg")
                .build();

        Cases entity = Cases.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .observation("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        CasesResponse response = CasesResponse.builder()
                .name("testing")
                .observation("what else?")
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Cases.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        CasesResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenCasesExists_DeleteCases() {
        UUID id = UUID.randomUUID();

        Cases cases = Cases.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(cases));

        service.delete(id);

        assertNotNull(cases.getDeletedAt());

        verify(repository).save(cases);
    }

    @Test
    public void deleteById_WhenCasesNotExists_DeleteCases() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenCasesExists_FindCases() {
        UUID id = UUID.randomUUID();

        Cases cases = Cases.builder()
                .id(id)
                .name("testing")
                .build();

        CasesResponse response = CasesResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(cases));
        when(mapper.toResponse(cases)).thenReturn(response);

        CasesResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenCasesNotExists_FindCases() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenCasesExists_Cases() {
        UUID id = UUID.randomUUID();

        UpdateCasesRequest updated = UpdateCasesRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .observation("test")
                .build();

        Cases cases = Cases.builder()
                .id(id)
                .name("old testing")
                .build();

        Cases saved = Cases.builder()
                .id(id)
                .name("testing")
                .build();

        CasesResponse response = CasesResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(cases));
        doNothing().when(mapper).UpdateEntityFromDto(updated, cases);
        when(repository.save(cases)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        CasesResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(cases.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, cases);
        verify(repository).save(cases);
    }

    @Test
    public void updateById_WhenCasesNotExists_UpdateCases() {
        UUID id = UUID.randomUUID();

        UpdateCasesRequest updated = UpdateCasesRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .observation("test")
                .build();


        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, updated));
        verify(repository, never()).save(any());
        verify(mapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenCasesExists_FindAllCases() {

        CasesFilter filter = new CasesFilter(
                List.of("some description"),
                List.of("testing"),
                List.of("test-slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Cases actionObject = Cases.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        CasesResponse response = CasesResponse.builder()
                .name("testing")
                .build();

        Page<Cases> page = new PageImpl<>(List.of(actionObject));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(actionObject)).thenReturn(response);

        Page<CasesResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenCasesNotExists_FindAllCases() {
        CasesFilter filter = new CasesFilter(
                null,  null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Cases> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<CasesResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
