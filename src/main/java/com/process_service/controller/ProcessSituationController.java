package com.process_service.controller;


import com.process_service.dto.*;
import com.process_service.services.ProcessSituationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/process-situation")
public class ProcessSituationController {
    public ProcessSituationService processService;

    public ProcessSituationController(ProcessSituationService processService) {
        this.processService = processService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProcessSituationResponse> createProcess(@RequestBody @Valid ProcessSituationDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(processService.createProcessSituation(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProcessSituationResponse> deleteProcess(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProcessSituationResponse> getProcess(@PathVariable UUID id) {
        return ResponseEntity.ok(processService.findById(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProcessSituationResponse> updateProcess(@PathVariable UUID id, @RequestBody @Valid UpdateProcessSituationRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                processService.updateById(id, dto)
        );
    }

    @GetMapping("/select")
    public ResponseEntity<List<ProcessSituationResponse>> selectProcess(
            @ModelAttribute ProcessSituationFilter filter) {

        return ResponseEntity.ok(processService.filterByInput(filter));
    }
}
