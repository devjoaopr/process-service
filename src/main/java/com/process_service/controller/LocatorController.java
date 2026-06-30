package com.process_service.controller;


import com.process_service.dto.Locator.LocatorDTO;
import com.process_service.dto.Locator.LocatorFilter;
import com.process_service.dto.Locator.LocatorResponse;
import com.process_service.dto.Locator.UpdateLocatorRequest;
import com.process_service.services.LocatorService;
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

@Tag(name = "Locator controller.", description = "This controller provides CRUD operations for locator. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/locator")
public class LocatorController {
    @Autowired
    private LocatorService service;

    @Operation(summary = "Creates locators", description = "create a new locator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locator created correctly",
                    content = @Content(schema = @Schema(implementation = LocatorResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating locator",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<LocatorResponse> create(@RequestBody @Valid LocatorDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "locator created successfully");
    }

    @Operation(summary = "Deletes a locator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "locator deleted correctly",
                    content = @Content(schema = @Schema(implementation = LocatorResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting locator",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<LocatorResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a locator by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "locator retrieved successfully",
                    content = @Content(schema = @Schema(implementation = LocatorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning locator",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<LocatorResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "locator found correctly");
    }

    @Operation(summary = "updates a locator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "locator updated correctly",
                    content = @Content(schema = @Schema(implementation = LocatorResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating locator",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<LocatorResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateLocatorRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "locator updated correctly"
        );
    }

    @Operation(summary = "filter locator", description = "this request can return and filter one or more locators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "locator found correctly",
                    content = @Content(schema = @Schema(implementation = LocatorResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error finding locators",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<LocatorResponse>> findAll(
            @ModelAttribute LocatorFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "locators found correctly");
    }
}
