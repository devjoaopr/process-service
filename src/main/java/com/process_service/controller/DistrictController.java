package com.process_service.controller;

import com.process_service.dto.District.DistrictDTO;
import com.process_service.dto.District.DistrictFilter;
import com.process_service.dto.District.DistrictResponse;
import com.process_service.dto.District.UpdateDistrictRequest;
import com.process_service.services.DistrictService;
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

@Tag(name = "District controller.", description = "This controller provides CRUD operations for districts. (create, read, update, delete, filter)")
@RestController
@RequestMapping("/district")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @Operation(summary = "Creates district", description = "create a new district")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "district created correctly",
                    content = @Content(schema = @Schema(implementation = DistrictResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error creating district",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PostMapping("/create")
    public StandardResponse<DistrictResponse> create(@RequestBody @Valid DistrictDTO dto) {
        return ApiResponseBuilder.success(districtService.createDistrict(dto), "district created successfully");
    }

    @Operation(summary = "Deletes a district")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "district deleted correctly",
                    content = @Content(schema = @Schema(implementation = DistrictResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting district",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public StandardResponse<DistrictResponse> delete(@PathVariable UUID id) {
        districtService.deleteById(id);
        return ApiResponseBuilder.success(null, "deleted successfully");
    }

    @Operation(summary = "Returns a district by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "District retrieved successfully",
                    content = @Content(schema = @Schema(implementation = DistrictResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "error returning district",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/get/{id}")
    public StandardResponse<DistrictResponse> get(@PathVariable UUID id) {
        return ApiResponseBuilder.success(districtService.findById(id), "district found correctly");
    }

    @Operation(summary = "updates a district")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "district updated correctly",
                    content = @Content(schema = @Schema(implementation = DistrictResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error updating district",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @PatchMapping("/update/{id}")
    public StandardResponse<DistrictResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateDistrictRequest dto) {
        return ApiResponseBuilder.success(
                districtService.updateById(id, dto), "district updated correctly"
        );
    }

    @Operation(summary = "filter districts", description = "this request can return and filter one or more districts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "district deleted correctly",
                    content = @Content(schema = @Schema(implementation = DistrictResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "error deleting district",
                    content = @Content(schema = @Schema(implementation = StandardResponse.class))
            )
    })
    @GetMapping("/select")
    public StandardResponse<PageResponse<DistrictResponse>> findAll(
            @ModelAttribute DistrictFilter filter,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponseBuilder.success(
                PageResponse.of(districtService.findAll(filter, pageable)), "districts found correctly");
    }
}
