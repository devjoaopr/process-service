package com.process_service.services;

import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupFilter;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.entity.Groups;
import com.process_service.mapper.GroupMapper;
import com.process_service.repository.GroupRepository;
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
class GroupServiceTest {

    @Mock
    private GroupRepository repository;

    @Mock
    private GroupMapper mapper;

    @InjectMocks
    private GroupService service;

    @Test
    public void groupService_createGroupService_ReturnsGroupDTO() {

        GroupDTO dto = GroupDTO.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .description("testing-description")
                .active(true)
                .displayOrder(1)
                .createdAt(OffsetDateTime.now())
                .build();


        Groups group = Groups.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        GroupResponse response = GroupResponse.builder()
                .build();

        when(mapper.toEntity(dto)).thenReturn(group);
        when(repository.save(any(Groups.class))).thenReturn(group);
        when(mapper.toResponse(group)).thenReturn(response);

        GroupResponse result = service.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenGroupExists_DeleteGroup() {
        UUID id = UUID.randomUUID();

        Groups group = Groups.builder()
                .id(id)
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(group));

        repository.deleteById(id);

        assertNotNull(group.getDeletedAt());

        verify(repository).save(group);

    }

    @Test
    public void deleteById_WhenGroupNotExists_DeleteGroup() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void findById_WhenDistrictExists_FindGroup() {
        UUID id = UUID.randomUUID();

        Groups group = Groups.builder()
                .id(id)
                .name("testing")
                .build();

        GroupResponse response = GroupResponse.builder()
                .name("testing")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(group));
        when(mapper.toResponse(group)).thenReturn(response);

        GroupResponse result = service.get(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenGroupNotExists_FindGroup() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(id));

        verify(repository, never()).save(any());
    }

    @Test
    public void updateById_WhenGroupExists_Group() {
        UUID id = UUID.randomUUID();

        UpdateGroupRequest updated = UpdateGroupRequest.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .description("testing-description")
                .active(true)
                .displayOrder(1)
                .createdAt(OffsetDateTime.now())
                .build();

        Groups group = Groups.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .updatedAt(OffsetDateTime.now())
                .build();

        Groups saved = Groups.builder()
                .id(id)
                .name("testing")
                .build();

        GroupResponse response = GroupResponse.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .description("testing-description")
                .active(true)
                .displayOrder(2)
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(group));
        doNothing().when(mapper).UpdateEntityFromDto(updated, group);
        when(repository.save(group)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        GroupResponse result = service.update(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(group.getUpdatedAt());
        verify(mapper).UpdateEntityFromDto(updated, group);
        verify(repository).save(group);
    }

    @Test
    public void updateById_WhenGroupNotExists_UpdateGroup() {
        UUID id = UUID.randomUUID();

        UpdateGroupRequest updated = UpdateGroupRequest.builder()
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
    void findAll_WhenGroupExists_FindAllGroup() {

        GroupFilter filter = new GroupFilter(
                List.of("testing"),
                List.of("name"),
                true,
                List.of("slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Groups group = Groups.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        GroupResponse response = GroupResponse.builder()
                .name("testing")
                .build();

        Page<Groups> page = new PageImpl<>(List.of(group));

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.toResponse(group)).thenReturn(response);

        Page<GroupResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(repository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenGroupNotExists_FindAllGroup() {
        GroupFilter filter = new GroupFilter(
                null,
                null,
                null,
                null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Groups> page = new PageImpl<>(List.of());

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<GroupResponse> result = service.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(mapper, never()).toResponse(any());
    }
}
