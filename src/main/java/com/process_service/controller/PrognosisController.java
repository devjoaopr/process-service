package com.process_service.controller;

import com.process_service.dto.Prognosis.PrognosisDTO;
import com.process_service.dto.Prognosis.PrognosisFilter;
import com.process_service.dto.Prognosis.PrognosisResponse;
import com.process_service.dto.Prognosis.UpdatePrognosisRequest;
import com.process_service.services.PrognosisService;
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

@Tag(name = "Prognosis controller.", description = "this controller provides CRUD operations for prognosis. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/prognosis")
public class PrognosisController {
    @Autowired
    private PrognosisService service;

    @Operation(summary = "Creates a prognosis.", description = "Create a new prognosis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prognosis created correctly.",
                    content = @Content(schema = @Schema(implementation = PrognosisResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating prognosis.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<PrognosisResponse> create(@RequestBody @Valid PrognosisDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Prognosis created succeswsfully.");
    }

    @Operation(summary = "Deletes a prognosis by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prognosis deleted correctly.",
                    content = @Content(schema = @Schema(implementation = PrognosisResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error deleting prognosis.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<PrognosisResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a prognosis by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prognosis retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = PrognosisResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning prognosis.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<PrognosisResponse> getById(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "Prognosis found correctly.");
    }

    @Operation(summary = "Updates a prognosis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prognois updated correctly.",
                    content = @Content(schema = @Schema(implementation = PrognosisResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating prognosis.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<PrognosisResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdatePrognosisRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "Prognosis updated correctly."
        );
    }

    @Operation(summary = "Filter prognosis.", description = "This request can return and filter one or more prognosis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prognosis returned correctly.",
                    content = @Content(schema = @Schema(implementation = PrognosisResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error returning prognosis.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<PrognosisResponse>> findAll(
            @ModelAttribute PrognosisFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Prognosis found correctly");
    }

}

