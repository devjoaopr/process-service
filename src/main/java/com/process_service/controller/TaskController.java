package com.process_service.controller;

import com.process_service.dto.Tasks.*;
import com.process_service.services.TaskService;
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
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Task controller.", description = "This controller provides CRUD operations for Tasks. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    @Operation(summary = "Creates a Task", description = "create a new taks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created correctly.",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error creating task.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping
    public StandardResponse<TaskResponse> create(@RequestBody @Valid TaskDTO dto) {
        return ApiResponseBuilder.success(service.create(dto), "Task created successfully");
    }

    @Operation(summary = "Deletes a task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted correctly.",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error deleting task.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public StandardResponse<TaskResponse> delete(@PathVariable UUID id) {
        service.delete(id);
        return ApiResponseBuilder.success(null, "Deleted successfully.");
    }

    @Operation(summary = "Returns a task by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Error returning task.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/{id}")
    public StandardResponse<TaskResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(service.get(id), "Task found correctly.");
    }

    @Operation(summary = "Updates a task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated correctly",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Error updating task.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/{id}")
    public StandardResponse<TaskResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateTaskRequest dto) {
        return ApiResponseBuilder.success(
                service.update(id, dto), "Task updated correctly."
        );
    }

    @Operation(summary = "Filter tasks.", description = "This request can return and filter one or more tasks.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task returned correctly.",
                    content = @Content(schema = @Schema(implementation = TaskResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error returning tasks.",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<TaskResponse>> findAll(
            @ModelAttribute TaskFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(service.findAll(filter, pageable)), "Task found correctly");
    }


}
