package com.process_service.controller;

import com.process_service.dto.Group.GroupDTO;
import com.process_service.dto.Group.GroupFilter;
import com.process_service.dto.Group.GroupResponse;
import com.process_service.dto.Group.UpdateGroupRequest;
import com.process_service.services.GroupService;
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

@Tag(name = "Group controller.", description = "This controller provides CRUD operations for Group. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService service;

    @Operation(summary = "Creates groups", description = "create a new group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group created correctly",
                    content = @Content(schema = @Schema(implementation = GroupResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating group",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<GroupResponse> create(@RequestBody @Valid GroupDTO dto) {
        return ApiResponseBuilder.success(service.createGroup(dto), "group created successfully");
    }

    @Operation(summary = "Deletes a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group deleted correctly",
                    content = @Content(schema = @Schema(implementation = GroupResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting group",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<GroupResponse> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a group by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group retrieved successfully",
                    content = @Content(schema = @Schema(implementation = GroupResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning group",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<GroupResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.findById(id), "group found correctly");
    }

    @Operation(summary = "updates a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group updated correctly",
                    content = @Content(schema = @Schema(implementation = GroupResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating group",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<GroupResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateGroupRequest dto) {
        return ApiResponseBuilder.success(
                service.updateById(id, dto), "groups updated correctly"
        );
    }

    @Operation(summary = "filter group", description = "this request can return and filter one or more groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "group deleted correctly",
                    content = @Content(schema = @Schema(implementation = GroupResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting group",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<GroupResponse>> findAll(
            @ModelAttribute GroupFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "groups found correctly");
    }

}
