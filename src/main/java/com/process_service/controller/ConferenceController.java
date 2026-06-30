package com.process_service.controller;

import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceFilter;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.services.ConferenceService;
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
@Tag(name = "Conference controller.", description = "This controller provides CRUD operations for conferences. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/conference")
public class ConferenceController {

    @Autowired
    private ConferenceService service;

    @Operation(summary = "Creates a conference", description = "create a new conference.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conference created correctly.",
                    content = @Content(schema = @Schema(implementation = ConferenceResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating conference.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<ConferenceResponse> create(@RequestBody @Valid ConferenceDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Conference created successfully");
    }

    @Operation(summary = "Deletes a conference.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conference deleted correctly.",
                    content = @Content(schema = @Schema(implementation = ConferenceResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error deleting conference.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<ConferenceResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a conference by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conference retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = ConferenceResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning conference.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<ConferenceResponse> getById(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "Conference found correctly.");
    }

    @Operation(summary = "Updates a conference.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conference updated correctly",
                    content = @Content(schema = @Schema(implementation = ConferenceResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating conference.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<ConferenceResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateConferenceRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "Conference updated correctly."
        );
    }

    @Operation(summary = "Filter conferences.", description = "This request can return and filter one or more conferences.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conference returned correctly.",
                    content = @Content(schema = @Schema(implementation = ConferenceResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error returning conference.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<ConferenceResponse>> findAll(
            @ModelAttribute ConferenceFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Conference found correctly");
    }

}

