package com.process_service.controller;
import com.process_service.dto.Phase.PhaseDTO;
import com.process_service.dto.Phase.PhaseFilter;
import com.process_service.dto.Phase.PhaseResponse;
import com.process_service.dto.Phase.UpdatePhaseRequest;
import com.process_service.services.PhaseService;
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

@Tag(name = "Phase controller.", description = "This controller provides CRUD operations for phase. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/phase")
public class PhaseController {

    @Autowired
    private PhaseService service;

    @Operation(summary = "Creates Phase", description = "create a new phase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phase created correctly",
                    content = @Content(schema = @Schema(implementation = PhaseResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating phase",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<PhaseResponse> create(@RequestBody @Valid PhaseDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "phase created successfully");
    }

    @Operation(summary = "Deletes a phase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phase deleted correctly",
                    content = @Content(schema = @Schema(implementation = PhaseResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting phase",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<PhaseResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a phase by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phase retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PhaseResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning phase",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<PhaseResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "phase found correctly");
    }

    @Operation(summary = "updates a phase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phase updated correctly",
                    content = @Content(schema = @Schema(implementation = PhaseResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating phase",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<PhaseResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdatePhaseRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "phase updated correctly"
        );
    }

    @Operation(summary = "filter phase", description = "this request can return and filter one or more phase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "phase found correctly",
                    content = @Content(schema = @Schema(implementation = PhaseResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error finding phase",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<PhaseResponse>> findAll(
            @ModelAttribute PhaseFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "phase found correctly");
    }
}
