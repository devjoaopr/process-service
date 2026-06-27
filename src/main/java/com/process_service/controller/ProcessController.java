package com.process_service.controller;

import com.process_service.dto.Process.ProcessDTO;
import com.process_service.dto.Process.ProcessFilter;
import com.process_service.dto.Process.ProcessResponse;
import com.process_service.dto.Process.UpdateProcessRequest;
import com.process_service.services.ProcessService;
import com.process_service.shared.ApiResponseBuilder;
import com.process_service.shared.PageResponse;
import com.process_service.shared.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    ProcessService processService;

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
    public StandardResponse<ProcessResponse> updateProcess(@PathVariable UUID id, @RequestBody @Valid UpdateProcessRequest dto) {
        return ApiResponseBuilder.success(
                processService.updateById(id, dto), "process updated correctly"
        );
    }

    @GetMapping("/select")
    public StandardResponse<PageResponse<ProcessResponse>> findAll(
            @ModelAttribute ProcessFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(processService.findAll(filter, pageable)), "process found correctly");
    }
}
