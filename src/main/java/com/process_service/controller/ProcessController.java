package com.process_service.controller;

import com.process_service.dto.Process.ProcessDTO;
import com.process_service.dto.Process.ProcessFilter;
import com.process_service.dto.Process.ProcessResponse;
import com.process_service.dto.Process.UpdateProcessRequest;
import com.process_service.services.ProcessService;
import com.process_service.shared.ApiResponseBuilder;
import com.process_service.shared.PageResponse;
import com.process_service.shared.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Process controller.", description = "This controller provides CRUD operations for process. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    ProcessService service;

    @PostMapping
    public StandardResponse<ProcessResponse> create(@RequestBody @Valid ProcessDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "process created successfully");
    }

    @DeleteMapping("/{id}")
    public StandardResponse<ProcessResponse> delete(@PathVariable UUID id) {
        return ApiResponseBuilder.success(null, "process deleted successfully");
    }

    @GetMapping("/{id}")
    public StandardResponse<ProcessResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.get(id), "process get successfully");
    }

    @PatchMapping("/{id}")
    public StandardResponse<ProcessResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateProcessRequest dto) {
        return ApiResponseBuilder.success(service.update(id, dto), "process updated successfully");
    }

    @GetMapping("/select")
    public StandardResponse<PageResponse<ProcessResponse>> findAll(
            @ModelAttribute ProcessFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "process found correctly");
    }
}
