package com.process_service.controller;

import com.process_service.dto.ProcessType.ProcessTypeDTO;
import com.process_service.dto.ProcessType.ProcessTypeFilter;
import com.process_service.dto.ProcessType.ProcessTypeResponse;
import com.process_service.dto.ProcessType.UpdateProcessTypeRequest;
import com.process_service.services.ProcessTypeService;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Process type controller.", description = "This controller provides CRUD operations for process type. (create, read, update, delete, filter)")

@RestController
@RequestMapping("/process-type")
public class ProcessTypeController {

    @Autowired
    private ProcessTypeService service;

    @Operation(summary = "Creates a process type.", description = "Create a new process type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process type created correctly.",
                    content = @Content(schema = @Schema(implementation = ProcessTypeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating process type.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<ProcessTypeResponse> create(@RequestBody @Valid ProcessTypeDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Process type created successfully.");
    }

    @Operation(summary = "Deletes a process type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process type deleted correctly.",
                    content = @Content(schema = @Schema(implementation = ProcessTypeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error deleting process type.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<ProcessTypeResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a process type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process type retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = ProcessTypeResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning process type.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<ProcessTypeResponse> getById(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "Process type found correctly.");
    }

    @Operation(summary = "Updates a process type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process type updated correctly",
                    content = @Content(schema = @Schema(implementation = ProcessTypeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating process type.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<ProcessTypeResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateProcessTypeRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "Process type updated correctly."
        );
    }

    @Operation(summary = "Filter process type.", description = "This request can return and filter one or more process type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Process type returned correctly.",
                    content = @Content(schema = @Schema(implementation = ProcessTypeResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error returning process type.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<ProcessTypeResponse>> findAll(
            @ModelAttribute ProcessTypeFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Process type found correctly");
    }

}
