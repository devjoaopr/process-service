package com.process_service.services;

import com.process_service.dto.ProcessResponse;
import com.process_service.dto.ProcessDTO;
import com.process_service.dto.UpdateProcessRequest;

import com.process_service.entity.Process;
import com.process_service.handlers.ResourceNotFoundException;
import com.process_service.mapper.ProcessMapper;
import com.process_service.repository.ProcessRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;


@Service
public class ProcessService {

    @Autowired
    ProcessMapper processMapper;
    @Autowired
    ProcessRepository repository;

    public ProcessResponse createProcess(ProcessDTO processDTO) {

        Process process = processMapper.toEntity(processDTO);

        process.setId(UUID.randomUUID());
        process.setCreatedAt(OffsetDateTime.now());

        Process saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

    public void deleteById(UUID id) {
        Process process = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado"));

        repository.delete(process);

    }

    public ProcessResponse findById(UUID id) {
        return processMapper.toResponse(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Processo nao encontrado")));
    }

    public ProcessResponse updateById(UUID id, UpdateProcessRequest request) {
        Process process = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No process found with id " + id));

        processMapper.updateEntityFromDto(request, process);

        Process saved = repository.save(process);

        return processMapper.toResponse(saved);
    }

}