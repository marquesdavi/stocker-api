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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "User management")
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Create a new user (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @CacheEvict(value = "user-find-all", allEntries = true)
    public void createUser(
            @Valid @RequestBody UserRequest user
    ) {
        userService.createUser(user);
    }

    @PatchMapping("/upload/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update user profile image by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Caching(evict = {
            @CacheEvict(value = "user-find-by-id", key = "#id"),
            @CacheEvict(value = "user-find-all", allEntries = true)
    })
    public void uploadFile(@PathVariable(name = "id") UUID id,
                             @RequestPart("file") MultipartFile file) {
        userService.uploadProfileImage(id, file);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "List all users (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users")
    })
    @Cacheable(value = "user-find-all")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Update a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Caching(evict = {
            @CacheEvict(value = "user-find-by-id", key = "#id"),
            @CacheEvict(value = "user-find-all", allEntries = true)
    })
    public void updateUser(
            @RequestBody UserRequest user,
            @PathVariable UUID id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Delete a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Caching(evict = {
            @CacheEvict(value = "user-find-by-id", key = "#id"),
            @CacheEvict(value = "user-find-all", allEntries = true)
    })
    public void deleteUserById(@PathVariable(name = "id") UUID id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Get a user by its ID (Staff Exclusive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @Cacheable(value = "user-find-by-id", key = "#id")
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
