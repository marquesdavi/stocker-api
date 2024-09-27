package com.stocker.api.controller;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
@Tag(name = "User", description = "User management")
public class UserController {

    private final UserService userService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public void createUser(@Valid @RequestBody UserRequest user) {
        userService.createUser(user);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List all users (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users")
    })
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void updateUser(
            @Valid @RequestBody UserRequest user,
            @PathVariable UUID id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void deleteUserById(@PathVariable(name = "id") UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponse getUserById(@PathVariable(name = "id") UUID id) {
        return userService.getUser(id);
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Returns the current logged user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponse getCurrentLoggedInUser() {
        return userService.getCurrentUser();
    }
}
