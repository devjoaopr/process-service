package com.process_service.controller;

import com.process_service.dto.SubjectOption.SubjectOptionDTO;
import com.process_service.dto.SubjectOption.SubjectOptionFilter;
import com.process_service.dto.SubjectOption.SubjectOptionResponse;
import com.process_service.dto.SubjectOption.UpdateSubjectOptionRequest;
import com.process_service.services.SubjectOptionService;
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

@Tag(name = "Subject option controller.", description = "This controller provides CRUD operations for subject options. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/subject-option")
public class SubjectOptionController {
    @Autowired
    private SubjectOptionService service;

    @Operation(summary = "Creates subject-options", description = "create a new subject-option")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "subject option created correctly",
                    content = @Content(schema = @Schema(implementation = SubjectOptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating subject option area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<SubjectOptionResponse> create(@RequestBody @Valid SubjectOptionDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "subject option created successfully");
    }

    @Operation(summary = "Deletes a subject option")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "subject option deleted correctly",
                    content = @Content(schema = @Schema(implementation = SubjectOptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting subject-option area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<SubjectOptionResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a subject-option by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "subject-option retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SubjectOptionResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning subject-option",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<SubjectOptionResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "subject-option found correctly");
    }

    @Operation(summary = "updates a subject-option area")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "subject-options  updated correctly",
                    content = @Content(schema = @Schema(implementation = SubjectOptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating subject-options ",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<SubjectOptionResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateSubjectOptionRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "subject-options updated correctly"
        );
    }

    @Operation(summary = "filter subject-options ", description = "this request can return and filter one or more subject-options ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "subject-options found correctly",
                    content = @Content(schema = @Schema(implementation = SubjectOptionResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error finding subject-options area",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<SubjectOptionResponse>> findAll(
            @ModelAttribute SubjectOptionFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "subject-optionsq area found correctly");
    }

}
