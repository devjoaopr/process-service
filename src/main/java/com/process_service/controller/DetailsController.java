package com.process_service.controller;

import com.process_service.dto.Detail.DetailDTO;
import com.process_service.dto.Detail.DetailFilter;
import com.process_service.dto.Detail.DetailResponse;
import com.process_service.dto.Detail.UpdateDetailRequest;
import com.process_service.services.DetailService;
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

@Tag(name = "Details controller.", description = "This controller provides CRUD operations for details. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/details")
public class DetailsController {

    @Autowired
    private DetailService service;

    @Operation(summary = "Creates a detail.", description = "Create a new detail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail created correctly.",
                    content = @Content(schema = @Schema(implementation = DetailResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating detail.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<DetailResponse> create(@RequestBody @Valid DetailDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Detail created successfully.");
    }

    @Operation(summary = "Deletes a detail by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail deleted correctly.",
                    content = @Content(schema = @Schema(implementation = DetailResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error deleting detail.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<DetailResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a detail by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = DetailResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning Detail.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<DetailResponse> getById(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "Detail found correctly.");
    }

    @Operation(summary = "Updates a detail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail updated correctly",
                    content = @Content(schema = @Schema(implementation = DetailResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating detail.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<DetailResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateDetailRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "Detail updated correctly."
        );
    }

    @Operation(summary = "Filter Details.", description = "This request can return and filter one or more details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detail returned correctly.",
                    content = @Content(schema = @Schema(implementation = DetailResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error returning detail.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<DetailResponse>> findAll(
            @ModelAttribute DetailFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Detail found correctly");
    }
}
