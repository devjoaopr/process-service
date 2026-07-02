package com.process_service.services;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectFilter;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.entity.ActionObject;
import com.process_service.mapper.ActionObjectMapper;
import com.process_service.repository.ActionObjectRepository;
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
class ActionObjectServiceTest {

    @Mock
    private ActionObjectRepository actionObjectRepository;

    @Mock
    private ActionObjectMapper actionObjectMapper;

    @InjectMocks
    private ActionObjectService actionObjectService;

    @Test
    public void ActionObjectService_createActionObjectService_ReturnsActionObjectDTO() {

        ActionObjectDTO dto = ActionObjectDTO.builder()
                .name("testing")
                .description("what else?")
                .slug("testinnnnggggg")
                .active(true)
                .build();

        ActionObject entity = ActionObject.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        ActionObjectResponse response = ActionObjectResponse.builder()
                .name("testing")
                .description("what else?")
                .build();

        when(actionObjectMapper.toEntity(dto)).thenReturn(entity);
        when(actionObjectRepository.save(any(ActionObject.class))).thenReturn(entity);
        when(actionObjectMapper.toResponse(entity)).thenReturn(response);

        ActionObjectResponse result = actionObjectService.createActionObject(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenActionObjectExists_DeleteActionObject() {
        UUID id = UUID.randomUUID();

        ActionObject process = ActionObject.builder()
                .id(id)
                .name("testing")
                .build();

        when(actionObjectRepository.findById(id)).thenReturn(Optional.of(process));

        actionObjectService.deleteById(id);

        assertNotNull(process.getDeletedAt());

        verify(actionObjectRepository).save(process);

    }

    @Test
    public void deleteById_WhenActionObjectNotExists_DeleteActionObject() {
        UUID id = UUID.randomUUID();

        when(actionObjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> actionObjectService.deleteById(id));

        verify(actionObjectRepository, never()).save(any());
    }

    @Test
    public void findById_WhenActionObjectExists_FindActionObject() {
        UUID id = UUID.randomUUID();

        ActionObject situation = ActionObject.builder()
                .id(id)
                .name("testing")
                .build();

        ActionObjectResponse response = ActionObjectResponse.builder()
                .name("testing")
                .build();

        when(actionObjectRepository.findById(id)).thenReturn(Optional.of(situation));
        when(actionObjectMapper.toResponse(situation)).thenReturn(response);

        ActionObjectResponse result = actionObjectService.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenActionObjectNotExists_FindActionObject() {
        UUID id = UUID.randomUUID();

        when(actionObjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> actionObjectService.findById(id));

        verify(actionObjectRepository, never()).save(any());
    }

    @Test
    public void updateById_WhenActionObjectExists_ActionObject() {
        UUID id = UUID.randomUUID();

        UpdateActionObjectRequest updated = UpdateActionObjectRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        ActionObject actionObject = ActionObject.builder()
                .id(id)
                .name("old testing")
                .build();

        ActionObject saved = ActionObject.builder()
                .id(id)
                .name("testing")
                .build();

        ActionObjectResponse response = ActionObjectResponse.builder()
                .name("testing")
                .build();

        when(actionObjectRepository.findById(id)).thenReturn(Optional.of(actionObject));
        doNothing().when(actionObjectMapper).UpdateEntityFromDto(updated, actionObject);
        when(actionObjectRepository.save(actionObject)).thenReturn(saved);
        when(actionObjectMapper.toResponse(saved)).thenReturn(response);

        ActionObjectResponse result = actionObjectService.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(actionObject.getUpdatedAt());
        verify(actionObjectMapper).UpdateEntityFromDto(updated, actionObject);
        verify(actionObjectRepository).save(actionObject);
    }

    @Test
    public void updateById_WhenActionObjectNotExists_UpdateActionObject() {
        UUID id = UUID.randomUUID();

        UpdateActionObjectRequest updated = UpdateActionObjectRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();


        when(actionObjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> actionObjectService.updateById(id, updated));
        verify(actionObjectRepository, never()).save(any());
        verify(actionObjectMapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenProcessSituationExists_FindAllProcessSituation() {

        ActionObjectFilter filter = new ActionObjectFilter(
                List.of("some description"),
                List.of("testing"),
                true,
                List.of("test-slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        ActionObject actionObject = ActionObject.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        ActionObjectResponse response = ActionObjectResponse.builder()
                .name("testing")
                .build();

        Page<ActionObject> page = new PageImpl<>(List.of(actionObject));

        when(actionObjectRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(actionObjectMapper.toResponse(actionObject)).thenReturn(response);

        Page<ActionObjectResponse> result = actionObjectService.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(actionObjectRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenActionObjectNotExists_FindAllActionObjects() {
        ActionObjectFilter filter = new ActionObjectFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<ActionObject> page = new PageImpl<>(List.of());

        when(actionObjectRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<ActionObjectResponse> result = actionObjectService.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(actionObjectMapper, never()).toResponse(any());
    }
}
