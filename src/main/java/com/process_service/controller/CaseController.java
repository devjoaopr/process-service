package com.process_service.controller;

import com.process_service.dto.Cases.CasesDTO;
import com.process_service.dto.Cases.CasesFilter;
import com.process_service.dto.Cases.CasesResponse;
import com.process_service.dto.Cases.UpdateCasesRequest;
import com.process_service.dto.Conference.ConferenceDTO;
import com.process_service.dto.Conference.ConferenceFilter;
import com.process_service.dto.Conference.ConferenceResponse;
import com.process_service.dto.Conference.UpdateConferenceRequest;
import com.process_service.mapper.CasesMapper;
import com.process_service.repository.CasesRepository;
import com.process_service.services.CaseService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Case controller.", description = "This controller provides CRUD operations for Cases. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/case")
public class CaseController {

    private final CaseService service;

    public CaseController(CaseService service) {
        this.service = service;
    }


    @Operation(summary = "Creates a Case", description = "create a new case.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Case created correctly.",
                    content = @Content(schema = @Schema(implementation = CasesResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating case.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping
    public StandardResponse<CasesResponse> create(@RequestBody @Valid CasesDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Case created successfully");
    }

    @Operation(summary = "Deletes a case.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Case deleted correctly.",
                    content = @Content(schema = @Schema(implementation = CasesResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error deleting case.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public StandardResponse<CasesResponse> delete(@PathVariable UUID id) {
        service.delete(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a case by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Case retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = CasesResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning case.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/{id}")
    public StandardResponse<CasesResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.get(id), "Case found correctly.");
    }

    @Operation(summary = "Updates a case.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Case updated correctly",
                    content = @Content(schema = @Schema(implementation = CasesResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating case.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/{id}")
    public StandardResponse<CasesResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateCasesRequest dto) {
        return ApiResponseBuilder.success(
                service.update(id, dto), "Case updated correctly."
        );
    }

    @Operation(summary = "Filter cases.", description = "This request can return and filter one or more cases.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cases returned correctly.",
                    content = @Content(schema = @Schema(implementation = CasesResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error returning cases.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<CasesResponse>> findAll(
            @ModelAttribute CasesFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Case found correctly");
    }


}
