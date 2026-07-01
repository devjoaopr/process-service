package com.process_service.services;

import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictFilter;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.entity.District;
import com.process_service.mapper.DistrictMapper;
import com.process_service.repository.DistrictRepository;
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
    private DistrictRepository districtRepository;

    @Mock
    private DistrictMapper districtMapper;

    @InjectMocks
    private DistrictService districtService;

    @Test
    public void districtService_createDistrictService_ReturnsDistrictDTO() {

        DistrictDTO dto = DistrictDTO.builder()
                .active(true)
                .judicialRank("testing-judicial")
                .cnjId("testing-cnj")
                .tjId("testing-tj")
                .internalId("testing-internal")
                .slug("testing-slug")
                .state("testing-state")
                .name("testing-name")
                .build();


        District entity = District.builder()
                .id(UUID.randomUUID())
                .name("testing-name")
                .slug("testing-slug")
                .createdAt(OffsetDateTime.now())
                .build();

        DistrictResponse response = DistrictResponse.builder()
                .build();

        when(districtMapper.toEntity(dto)).thenReturn(entity);
        when(districtRepository.save(any(District.class))).thenReturn(entity);
        when(districtMapper.toResponse(entity)).thenReturn(response);

        DistrictResponse result = districtService.createDistrict(dto);

        assertNotNull(result);
        assertEquals("testing", result.name());
    }

    @Test
    public void deleteById_WhenDistrictExists_DeleteDistrict() {
        UUID id = UUID.randomUUID();

        District process = District.builder()
                .id(id)
                .name("testing")
                .build();

        when(districtRepository.findById(id)).thenReturn(Optional.of(process));

        districtRepository.deleteById(id);

        assertNotNull(process.getDeletedAt());

        verify(districtRepository).save(process);

    }

    @Test
    public void deleteById_WhenDistrictNotExists_DeleteDistrict() {
        UUID id = UUID.randomUUID();

        when(districtRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> districtService.deleteById(id));

        verify(districtRepository, never()).save(any());
    }

    @Test
    public void findById_WhenDistrictExists_FindDistrict() {
        UUID id = UUID.randomUUID();

        District situation = District.builder()
                .id(id)
                .name("testing")
                .build();

        DistrictResponse response = DistrictResponse.builder()
                .name("testing")
                .build();

        when(districtRepository.findById(id)).thenReturn(Optional.of(situation));
        when(districtMapper.toResponse(situation)).thenReturn(response);

        DistrictResponse result = districtService.findById(id);

        assertNotNull(result);
        assertEquals("testing", result.name());

    }

    @Test
    public void findById_WhenDistrictNotExists_FindDistrict() {
        UUID id = UUID.randomUUID();

        when(districtRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> districtService.findById(id));

        verify(districtRepository, never()).save(any());
    }

    @Test
    public void updateById_WhenDistrictExists_District() {
        UUID id = UUID.randomUUID();

        UpdateDistrictRequest updated = UpdateDistrictRequest.builder()
                .createdAt(OffsetDateTime.now())
                .active(true)
                .judicialRank("testing-judicial")
                .cnjId("testing-cnj")
                .tjId("testing-tj")
                .internalId("testing-internal")
                .slug("testing-slug")
                .state("testing-state")
                .name("testing-name")
                .build();

        District actionObject = District.builder()
                .updatedAt(OffsetDateTime.now())
                .active(true)
                .judicialRank("testing-judicial")
                .cnjId("testing-cnj")
                .tjId("testing-tj")
                .internalId("testing-internal")
                .slug("testing-slug")
                .state("testing-state")
                .name("testing-name")
                .build();

        District saved = District.builder()
                .id(id)
                .name("testing")
                .build();

        DistrictResponse response = DistrictResponse.builder()
                .active(true)
                .judicialRank("testing-judicial")
                .cnjId("testing-cnj")
                .tjId("testing-tj")
                .internalId("testing-internal")
                .slug("testing-slug")
                .state("testing-state")
                .name("testing-name")
                .build();

        when(districtRepository.findById(id)).thenReturn(Optional.of(actionObject));
        doNothing().when(districtMapper).UpdateEntityFromDto(updated, actionObject);
        when(districtRepository.save(actionObject)).thenReturn(saved);
        when(districtMapper.toResponse(saved)).thenReturn(response);

        DistrictResponse result = districtService.updateById(id, updated);

        assertNotNull(result);
        assertEquals("testing", result.name());
        assertNotNull(actionObject.getUpdatedAt());
        verify(districtMapper).UpdateEntityFromDto(updated, actionObject);
        verify(districtRepository).save(actionObject);
    }

    @Test
    public void updateById_WhenDistrictNotExists_UpdateDistrict() {
        UUID id = UUID.randomUUID();

        UpdateDistrictRequest updated = UpdateDistrictRequest.builder()
                .updatedAt(OffsetDateTime.now())
                .name("testing")
                .slug("test")
                .build();


        when(districtRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> districtService.updateById(id, updated));
        verify(districtRepository, never()).save(any());
        verify(districtMapper, never()).UpdateEntityFromDto(any(), any());
    }

    @Test
    void findAll_WhenDistrictsExists_FindAllDistricts() {

        DistrictFilter filter = new DistrictFilter(
                null,
                null,
                null,
                true,
                "testing",
                List.of("testing-cnj"),
                List.of("testing-tj"),
                List.of("testing-internal"),
                List.of("testing-slug"),
                List.of("testing-state"),
                ("stand")
        );

        Pageable pageable = PageRequest.of(0, 10);

        District district = District.builder()
                .id(UUID.randomUUID())
                .name("testing")
                .build();

        DistrictResponse response = DistrictResponse.builder()
                .name("testing")
                .build();

        Page<District> page = new PageImpl<>(List.of(district));

        when(districtRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(districtMapper.toResponse(district)).thenReturn(response);

        Page<DistrictResponse> result = districtService.findAll(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("testing", result.getContent().getFirst().name());
        verify(districtRepository).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findAll_WhenDistrictNotExists_FindAllDistricts() {
        DistrictFilter filter = new DistrictFilter(
                null, null, null, null, null, null, null, null, null, null, null
                );
        Pageable pageable = PageRequest.of(0, 10);

        Page<District> page = new PageImpl<>(List.of());

        when(districtRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);

        Page<DistrictResponse> result = districtService.findAll(filter, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(districtMapper, never()).toResponse(any());
    }
}
