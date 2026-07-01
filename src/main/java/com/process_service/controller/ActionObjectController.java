package com.process_service.controller;

import com.process_service.dto.ActionObject.ActionObjectDTO;
import com.process_service.dto.ActionObject.ActionObjectFilter;
import com.process_service.dto.ActionObject.ActionObjectResponse;
import com.process_service.dto.ActionObject.UpdateActionObjectRequest;
import com.process_service.services.ActionObjectService;
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

@Tag(name = "Action object controller.", description = "This controller provides CRUD operations for action object. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/action-object")
public class ActionObjectController {
    @Autowired
    private ActionObjectService service;

    @Operation(summary = "Creates action object", description = "create a new action object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "action object created correctly",
                    content = @Content(schema = @Schema(implementation = ActionObjectResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating action object",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<ActionObjectResponse> create(@RequestBody @Valid ActionObjectDTO dto) {
        return ApiResponseBuilder.success(service.createActionObject(dto), "action object created successfully");
    }

    @Operation(summary = "Deletes a action object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "action object deleted correctly",
                    content = @Content(schema = @Schema(implementation = ActionObjectResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting action object",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<ActionObjectResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a action object by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "action object retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ActionObjectResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning action object",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<ActionObjectResponse> getById(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "Action object found correctly");
    }

    @Operation(summary = "updates a action object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action object updated correctly",
                    content = @Content(schema = @Schema(implementation = ActionObjectResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating action object",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<ActionObjectResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateActionObjectRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "Action object updated correctly"
        );
    }

    @Operation(summary = "filter Action object", description = "this request can return and filter one or more action objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "action object returned correctly",
                    content = @Content(schema = @Schema(implementation = ActionObjectResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error returning action objects",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<ActionObjectResponse>> findAll(
            @ModelAttribute ActionObjectFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Action object found correctly");
    }

}
