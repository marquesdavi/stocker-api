package com.stocker.api.controller;

import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.dto.movement.MovementResponse;
import com.stocker.api.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movements")
@Tag(name = "Movement", description = "Movement management")
public class MovementController {

    private final MovementService movementService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new movement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movement created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void createMovement(@Valid @RequestBody MovementRequest movementRequest) {
        movementService.createMovement(movementRequest);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all movements")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movements")
    })
    public List<MovementResponse> getMovements() {
        return movementService.getMovements();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a movement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movement updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Movement not found")
    })
    public void updateMovement(
            @Valid @RequestBody MovementRequest movementRequest,
            @PathVariable UUID id) {
        movementService.updateMovement(id, movementRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a movement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movement deleted"),
            @ApiResponse(responseCode = "404", description = "Movement not found")
    })
    public void deleteMovementById(@PathVariable(name = "id") UUID id) {
        movementService.deleteMovement(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a movement by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movement found"),
            @ApiResponse(responseCode = "404", description = "Movement not found")
    })
    public MovementResponse getMovementById(@PathVariable(name = "id") UUID id) {
        return movementService.getMovementById(id);
    }
}
