package com.process_service.controller;

import com.process_service.dto.*;
import com.process_service.services.ProcessService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/process")
public class ProcessController {

    public ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProcessResponse> createProcess(@RequestBody @Valid ProcessDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(processService.createProcess(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProcessResponse> deleteProcess(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProcessResponse> getProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(processService.findById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProcessResponse> updateProcess(@PathVariable UUID id, @RequestBody @Valid UpdateProcessRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                processService.updateById(id, dto)
        );
    }

    @GetMapping("/select")
    public ResponseEntity<List<ProcessResponse>> selectProcess(
            @ModelAttribute ProcessFilter filter) {
        return ResponseEntity.ok(processService.findAll(filter));
    }
}
