package com.process_service.controller;

import com.process_service.dto.PracticeArea.PracticeAreaDTO;
import com.process_service.dto.PracticeArea.PracticeAreaFilter;
import com.process_service.dto.PracticeArea.PracticeAreaResponse;
import com.process_service.dto.PracticeArea.UpdatePracticeAreaRequest;
import com.process_service.services.PracticeAreaService;
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

@Tag(name = "Practice area controller.", description = "This controller provides CRUD operations for practice areas. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/practice-area")
public class PracticeAreaController {
    @Autowired
    private PracticeAreaService service;

    @Operation(summary = "Creates practice area", description = "create a new practice area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "practice area created correctly",
                    content = @Content(schema = @Schema(implementation = PracticeAreaResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating practice area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<PracticeAreaResponse> create(@RequestBody @Valid PracticeAreaDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "practice area created successfully");
    }

    @Operation(summary = "Deletes a practice area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "practice area deleted correctly",
                    content = @Content(schema = @Schema(implementation = PracticeAreaResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting practice area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<PracticeAreaResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a practice-area by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "practice area retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PracticeAreaResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning practice area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<PracticeAreaResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "practice area found correctly");
    }

    @Operation(summary = "updates a practice area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "practice area updated correctly",
                    content = @Content(schema = @Schema(implementation = PracticeAreaResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating practice area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<PracticeAreaResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdatePracticeAreaRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "practice area updated correctly"
        );
    }

    @Operation(summary = "filter practice area", description = "this request can return and filter one or more practice area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "practice area found correctly",
                    content = @Content(schema = @Schema(implementation = PracticeAreaResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error finding practice area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<PracticeAreaResponse>> findAll(
            @ModelAttribute PracticeAreaFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "practice area found correctly");
    }

}
