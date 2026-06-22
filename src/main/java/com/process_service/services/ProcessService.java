package com.process_service.services;

import com.process_service.dto.ProcessResponse;
import com.process_service.dto.ProcessDTO;
import com.process_service.entity.Process;
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

    public ProcessResponse deleteById(UUID id) {
        Process process = repository.findById(id).orElse(null);
        if (process != null) {
            repository.delete(process);
        }
        return null;
    }



}