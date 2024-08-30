package com.stocker.api.controller;

import com.stocker.api.domain.dto.movement.MovementRequest;
import com.stocker.api.domain.entity.Movement;
import com.stocker.api.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {

    private final MovementService movementService;

    @PostMapping
    public void createMovement(
            @RequestBody MovementRequest request) {
        try{
            movementService.createMovement(request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
