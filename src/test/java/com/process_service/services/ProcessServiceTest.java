package com.process_service.services;

import com.process_service.dto.ProcessSituation.ProcessSituationDTO;
import com.process_service.dto.ProcessSituation.ProcessSituationResponse;
import com.process_service.dto.ProcessSituation.UpdateProcessSituationRequest;
import com.process_service.entity.ProcessSituation;
import com.process_service.mapper.ProcessSituationMapper;
import com.process_service.repository.ProcessSituationRepository;
import com.process_service.shared.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessServiceTest {

    @Mock
    private ProcessSituationRepository processRepository;

    @Mock
    private ProcessSituationMapper processMapper;

    @InjectMocks
    private ProcessSituationService processService;

    @Test
    public void ProcessService_createProcessService_ReturnsProcessSituationDTO() {

        ProcessSituationDTO dto = ProcessSituationDTO.builder()
                .name("testing")
                .description("what else?")
                .slug("testinnnnggggg")
                .active(true)
                .build();

        ProcessSituation entity = ProcessSituation.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .description("what else?")
                .slug("testinnnnggggg")
                .active(true)
                .createdAt(OffsetDateTime.now())
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .name("testing")
                .description("what else?")
                .build();

        when(processMapper.toEntity(dto)).thenReturn(entity);
        when(processRepository.save(any(ProcessSituation.class))).thenReturn(entity);
        when(processMapper.toResponse(entity)).thenReturn(response);

        ProcessSituationResponse result = processService.createProcessSituation(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenProcessSituationExists_DeleteProcessSituation() {
        UUID id = UUID.randomUUID();

        ProcessSituation process = ProcessSituation.builder()
                .id(id)
                .name("testing")
                .build();

    when(processRepository.findById(id)).thenReturn(Optional.of(process));

    processService.deleteById(id);

    assertNotNull(process.getDeletedAt());

    verify(processRepository).save(process);

    }

    @Test
    public void deleteById_WhenProcessSituationNotExists_DeleteProcessSituation() {
        //situacao dada (id) GIVEN
        UUID id = UUID.randomUUID();

        //ensina o mock a retornar vazio quando a funcao for chamada WHEN
        when(processRepository.findById(id)).thenReturn(Optional.empty());

        //verifica qquando a exceptiuon eh lancada WHEN+THEN
        assertThrows(ResourceNotFoundException.class, () -> processService.deleteById(id));

        //garante que o save nunca foi chamado, oq deu erro antes
        verify(processRepository, never()).save(any());
    }

    @Test
    public void findById_WhenProcessSituationExists_FindProcessSituation() {
        UUID id = UUID.randomUUID();

        ProcessSituation situation = ProcessSituation.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                        .name("testing")
                        .build();

        when(processRepository.findById(id)).thenReturn(Optional.of(situation));
        when(processMapper.toResponse(situation)).thenReturn(response);

        ProcessSituationResponse result = processService.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenProcessSituationNotExists_FindProcessSituation() {
        UUID id = UUID.randomUUID();

        when(processRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> processService.findById(id));

        verify(processRepository, never()).save(any());
    }

    @Test
    public void updateById_WhenProcessSituationExists_UpdateProcessSituation() {
        UUID id = UUID.randomUUID();

        UpdateProcessSituationRequest updated = UpdateProcessSituationRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();

        ProcessSituation process = ProcessSituation.builder()
                .id(id)
                .name("old testing")
                .build();

        ProcessSituation saved = ProcessSituation.builder()
                .id(id)
                .name("testing")
                .build();

        ProcessSituationResponse response = ProcessSituationResponse.builder()
                .name("testing")
                .build();

        when(processRepository.findById(id)).thenReturn(Optional.of(process));
        doNothing().when(processMapper).updateEntityFromDto(updated, process);
        when(processRepository.save(process)).thenReturn(saved);
        when(processMapper.toResponse(saved)).thenReturn(response);

        ProcessSituationResponse result = processService.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(process.getUpdatedAt());
        verify(processMapper).updateEntityFromDto(updated, process);
        verify(processRepository).save(process);
    }

}
