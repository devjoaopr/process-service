package com.process_service.controller;

import com.process_service.dto.ProcessSituation.ProcessSituationDTO;
import com.process_service.dto.ProcessSituation.ProcessSituationFilter;
import com.process_service.dto.ProcessSituation.ProcessSituationResponse;
import com.process_service.dto.ProcessSituation.UpdateProcessSituationRequest;
import com.process_service.services.ProcessSituationService;
import com.process_service.shared.ApiResponseBuilder;
import com.process_service.shared.PageResponse;
import com.process_service.shared.StandardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Process situation controller.", description = "this controller provides CRUD operations for process situation. (create, read, update, delete, filter)")
@Controller
@RequestMapping("/process-situation")
public class ProcessSituationController {

    @Autowired
    public ProcessSituationService service;

    public ProcessSituationController(ProcessSituationService processService) {
        this.service = processService;
    }

    @Operation(summary = "create process situation", description = "creates a new process situation")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Process situation created correctly.",
                    content = @Content(schema = @Schema(implementation = ProcessSituationResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error creating process situation",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping
    public StandardResponse<ProcessSituationResponse> create(@RequestBody @Valid ProcessSituationDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Process situation created correctly.");
    }

    @Operation(summary = "delete process situation", description = "this operation deletes a process situation")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process situation deleted correctly"
            ),
            @ApiResponse(responseCode = "400", description = "error deleting process situation"
            )
    })
    @DeleteMapping("/{id}")
    public StandardResponse<ProcessSituationResponse> delete(@PathVariable UUID id) {
        return ApiResponseBuilder.success(null, "Process situation deleted correctly.");
    }

    @Operation(summary = "return process situation", description = "this operation returns a process situation with his ID")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "process returned correctly"
            ),
            @ApiResponse(responseCode = "404", description = "error creating process situation"
            )
    })
    @GetMapping("/{id}")
    public StandardResponse<ProcessSituationResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.get(id),  "Process situation get correctly.");
    }

    @Operation(summary = "update process situation", description = "this operation returns a process situation with his ID")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process updated correctly", content = @Content(schema = @Schema(implementation = UpdateProcessSituationRequest.class))

            ),
            @ApiResponse(responseCode = "400", description = "error updating process situation"
            )
    })
    @PatchMapping("/{id}")
    public StandardResponse<ProcessSituationResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateProcessSituationRequest dto) {
        return ApiResponseBuilder.success(
                service.update(id, dto), "process situation updated"
        );
    }

    @Operation(summary = "update process situation", description = "this operation returns a process situation with his ID")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process updated correctly", content = @Content(schema = @Schema(implementation = UpdateProcessSituationRequest.class))

            ),
            @ApiResponse(responseCode = "400", description = "error updating process situation"
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<ProcessSituationResponse>> findAll(
            @ModelAttribute ProcessSituationFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "districts found correctly");
    }
}
