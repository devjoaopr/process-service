package com.process_service.controller;


import com.process_service.dto.District.DistrictFilter;
import com.process_service.dto.District.DistrictResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Process situation controller.", description = "this controller provides CRUD operations for process situation. (create, read, update, delete, filter)")
@Controller
@RequestMapping("/process-situation")
public class ProcessSituationController {

    @Autowired
    public ProcessSituationService processService;

    public ProcessSituationController(ProcessSituationService processService) {
        this.processService = processService;
    }

    @Operation(summary = "create process situation", description = "creates a new process situation")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "process wsituation created correctly",
                    content = @Content(schema = @Schema(implementation = ProcessSituationResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error creating process situation",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ProcessSituationResponse> create(@RequestBody @Valid ProcessSituationDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(processService.createProcessSituation(dto));
    }

    @Operation(summary = "delete process situation", description = "this operation deletes a process situation")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process situation deleted correctly"
            ),
            @ApiResponse(responseCode = "400", description = "error deleting process situation"
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProcessSituationResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "return process situation", description = "this operation returns a process situation with his ID")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process returned correctly"
            ),
            @ApiResponse(responseCode = "400", description = "error creating process situation"
            )
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<ProcessSituationResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(processService.findById(id));
    }

    @Operation(summary = "update process situation", description = "this operation returns a process situation with his ID")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "process updated correctly", content = @Content(schema = @Schema(implementation = UpdateProcessSituationRequest.class))

            ),
            @ApiResponse(responseCode = "400", description = "error updating process situation"
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<ProcessSituationResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateProcessSituationRequest dto) {
        return ApiResponseBuilder.success(
                processService.updateById(id, dto), "process situation updated"
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
                PageResponse.of(processService.findAll(filter, pageable)), "districts found correctly");
    }
}
