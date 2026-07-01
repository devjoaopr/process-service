package com.process_service.services;


import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceFilter;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.entity.Conference;
import com.process_service.mapper.ConferenceMapper;
import com.process_service.repository.ConferenceRepository;
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
class ConferenceServiceTest {

    @Mock
    private ConferenceRepository conferenceRepository;

    @Mock
    private ConferenceMapper conferenceMapper;

    @InjectMocks
    private ConferenceService conferenceService;

    @Test
    public void ActionObjectService_createConferenceService_ReturnsConferenceDTO() {

        ConferenceDTO dto = ConferenceDTO.builder()
                .name("testing")
                .description("what else?")
                .slug("testinnnnggggg")
                .active(true)
                .build();

        Conference entity = Conference.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        ConferenceResponse response = ConferenceResponse.builder()
                .name("testing")
                .description("what else?")
                .build();

        when(conferenceMapper.toEntity(dto)).thenReturn(entity);
        when(conferenceRepository.save(any(Conference.class))).thenReturn(entity);
        when(conferenceMapper.toResponse(entity)).thenReturn(response);

        ConferenceResponse result = conferenceService.create(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenConferenceExists_DeleteConference() {
        UUID id = UUID.randomUUID();

        Conference process = Conference.builder()
                .id(id)
                .name("testing")
                .build();

        when(conferenceRepository.findById(id)).thenReturn(Optional.of(process));

        conferenceService.deleteById(id);

        assertNotNull(process.getDeletedAt());

        verify(conferenceRepository).save(process);

    }

    @Test
    public void deleteById_WhenConferenceNotExists_DeleteConference() {
        UUID id = UUID.randomUUID();

        when(conferenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> conferenceService.deleteById(id));

        verify(conferenceRepository, never()).save(any());
    }

    @Test
    public void findById_WhenConferenceExists_FindConference() {
        UUID id = UUID.randomUUID();

        Conference situation = Conference.builder()
                .id(id)
                .name("testing")
                .build();

        ConferenceResponse response = ConferenceResponse.builder()
                .name("testing")
                .build();

        when(conferenceRepository.findById(id)).thenReturn(Optional.of(situation));
        when(conferenceMapper.toResponse(situation)).thenReturn(response);

        ConferenceResponse result = conferenceService.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenConferenceNotExists_FindConference() {
        UUID id = UUID.randomUUID();

        when(conferenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> conferenceService.findById(id));

        verify(conferenceRepository, never()).save(any());
    }

    @Test
    public void updateById_WhenConferenceExists() {
        UUID id = UUID.randomUUID();

        UpdateConferenceRequest updated = UpdateConferenceRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        Conference conference = Conference.builder()
                .id(id)
                .name("old testing")
                .build();

        Conference saved = Conference.builder()
                .id(id)
                .name("testing")
                .build();

        ConferenceResponse response = ConferenceResponse.builder()
                .name("testing")
                .build();

        when(conferenceRepository.findById(id)).thenReturn(Optional.of(conference));
        doNothing().when(conferenceMapper).UpdateEntityFromDto(updated, conference);
        when(conferenceRepository.save(conference)).thenReturn(saved);
        when(conferenceMapper.toResponse(saved)).thenReturn(response);

        ConferenceResponse result = conferenceService.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(conference.getUpdatedAt());
        verify(conferenceMapper).UpdateEntityFromDto(updated, conference);
        verify(conferenceRepository).save(conference);
    }

    @Test
    public void updateById_WhenConferenceNotExists_UpdateConference() {
        UUID id = UUID.randomUUID();

        UpdateConferenceRequest updated = UpdateConferenceRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();


        when(conferenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> conferenceService.updateById(id, updated));
        verify(conferenceRepository, never()).save(any());
        verify(conferenceMapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenConferenceExists_FindAllConferences() {

        ConferenceFilter filter = new ConferenceFilter(
                List.of("some description"),
                List.of("testing"),
                true,
                List.of("test-slug")
        );

        Pageable pageable = PageRequest.of(0, 10);

        Conference conference = Conference.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        ConferenceResponse response = ConferenceResponse.builder()
                .name("testing")
                .build();

        Page<Conference> page = new PageImpl<>(List.of(conference));

        when(conferenceRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(conferenceMapper.toResponse(conference)).thenReturn(response);

        Page<ConferenceResponse> result = conferenceService.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(conferenceRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenActionObjectNotExists_FindAllActionObjects() {
        ConferenceFilter filter = new ConferenceFilter(
                null, null, null, null
        );
        Pageable pageable = PageRequest.of(0, 10);

        Page<Conference> page = new PageImpl<>(List.of());

        when(conferenceRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<ConferenceResponse> result = conferenceService.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(conferenceMapper, never()).toResponse(any());
    }
}
