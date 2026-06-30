package com.process_service.controller;

import com.process_service.dto.Origin.OriginDTO;
import com.process_service.dto.Origin.OriginFilter;
import com.process_service.dto.Origin.OriginResponse;
import com.process_service.dto.Origin.UpdateOriginRequest;
import com.process_service.entity.Origin;
import com.process_service.services.OriginService;
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

@Tag(name = "Origin controller.", description = "This controller provides CRUD operations for origin. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/origin")
public class OriginController {
    @Autowired
    private OriginService service;

    @Operation(summary = "Creates Origin", description = "create a new origin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "origin created correctly",
                    content = @Content(schema = @Schema(implementation = OriginResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating Origin",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<OriginResponse> create(@RequestBody @Valid OriginDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "origin created successfully");
    }

    @Operation(summary = "Deletes a origin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "origin deleted correctly",
                    content = @Content(schema = @Schema(implementation = OriginResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting origin",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<OriginResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a origin by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "origin retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OriginResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning origin",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<OriginResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "origin found correctly");
    }

    @Operation(summary = "updates a origin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "origin updated correctly",
                    content = @Content(schema = @Schema(implementation = OriginResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating origin",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<OriginResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateOriginRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "origin updated correctly"
        );
    }

    @Operation(summary = "filter origin", description = "this request can return and filter one or more origins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "origin found correctly",
                    content = @Content(schema = @Schema(implementation = OriginResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error finding origin",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<OriginResponse>> findAll(
            @ModelAttribute OriginFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "origins found correctly");
    }
}
