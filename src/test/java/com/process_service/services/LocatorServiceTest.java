package com.process_service.services;

import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorFilter;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.entity.Locators;
import com.process_service.mapper.LocatorMapper;
import com.process_service.repository.LocatorRepository;
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
class LocatorServiceTest {

    @Mock
    private LocatorRepository repository;

    @Mock
    private LocatorMapper mapper;

    @InjectMocks
    private LocatorService service;

    @Test
    public void locatorService_createLocatorService_ReturnsLocatorDTO() {

        LocatorDTO dto = LocatorDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-locator")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();


        Locators entity = Locators.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        LocatorResponse response = LocatorResponse.builder()
                .build();

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repository.save(any(Locators.class))).thenReturn(entity);
        when(mapper.toResponse(entity)).thenReturn(response);

        LocatorResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenLocatorExists_DeleteLocator() {
        UUID id = UUID.randomUUID();

        Locators process = Locators.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(process));

        repository.deleteById(id);

        assertNotNull(process.getDeletedAt());

        verify(repository).save(process);

    }

    @Test
    public void deleteById_WhenLocatorNotExists_DeleteLocator() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenLocatorExists_FindLocator() {
        UUID id = UUID.randomUUID();

        Locators situation = Locators.builder()
                .id(id)
                .name("testing")
                .build();

        LocatorResponse response = LocatorResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(situation));
        when(mapper.toResponse(situation)).thenReturn(response);

        LocatorResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenLocatorNotExists_FindLocator() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenLocatorExists_Locator() {
        UUID id = UUID.randomUUID();

        UpdateLocatorRequest updated = UpdateLocatorRequest.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .description("testing-description")
                .active(true)
                .displayOrder(1)
                .updatedAt(OffsetDateTime.now())
                .build();

        Locators locator = Locators.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Locators saved = Locators.builder()
                .id(id)
                .name("testing")
                .build();

        LocatorResponse response = LocatorResponse.builder()
                .id(id)
                .name("testing-name")
                .slug("testing-slug")
                .description("testing-description")
                .active(true)
                .displayOrder(2)
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(locator));
        doNothing().when(mapper).UpdateEntityFromDto(updated, locator);
        when(repository.save(locator)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        LocatorResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(locator.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, locator);
        verify(repository).save(locator);
    }

    @Test
    public void updateById_WhenLocatorNotExists_UpdateLocator() {
        UUID id = UUID.randomUUID();

        UpdateLocatorRequest updated = UpdateLocatorRequest.builder()
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
    void findAll_WhenLocatorExists_FindAllLocators() {

        LocatorFilter filter = new LocatorFilter(
                List.of("description"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Locators locator = Locators.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        LocatorResponse response = LocatorResponse.builder()
                .name("testing")
                .build();

        Page<Locators> page = new PageImpl<>(List.of(locator));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(locator)).thenReturn(response);

        Page<LocatorResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenLocatorNotExists_FindAllLocators() {
        LocatorFilter filter = new LocatorFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Locators> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<LocatorResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
