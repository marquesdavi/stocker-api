package com.stocker.api.controller;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.entity.User;
import com.stocker.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "User management")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void createUser(@Valid @RequestBody UserRequest user) {
        try {
            userService.createUser(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all users (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<User> getUsers() {
        try {
            return userService.getUsers();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void updateUser(@Valid @RequestBody UserRequest user) {

    }

    public void deleteUserById(@PathVariable UUID id) {

    }

    public User getUserById(@PathVariable UUID id) {
        return null;
    }
}
