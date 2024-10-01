package com.stocker.api.service.impl;

import com.stocker.api.config.security.IAuthenticationFacade;
import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.dto.user.UserResponse;
import com.stocker.api.domain.entity.Role;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.repository.UserRepository;
import com.stocker.api.domain.shared.DefaultMapper;
import com.stocker.api.exception.exceptions.ResourceNotFoundException;
import com.stocker.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final DefaultMapper<User, UserRequest, UserResponse> defaultMapper;

    @Override
    public void createUser(UserRequest user) {
        existsByCpfOrEmail(user);

        User instance = defaultMapper.toDomain(user);
        instance.setRoles(Set.of(Role.USER));

        userRepository.save(instance);
    }

    @Override
    public void uploadProfileImage(UUID id, MultipartFile file) {
        User user = findByIdOrElseThrow(id);

        try {
            byte[] imageBytes = file.getBytes();
            user.setProfileImage(imageBytes);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar a imagem", e);
        }
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(defaultMapper::toResponse).toList();
    }

    @Override
    public UserResponse getUser(UUID id) {
        return defaultMapper.toResponse(findByIdOrElseThrow(id));
    }

    @Override
    public UserResponse getCurrentUser() {
        return defaultMapper.toResponse(getAuthenticatedUserOrElseThrow());
    }

    @Override
    public void updateUser(UserRequest user, UUID id) {
        User instance = findByIdOrElseThrow(id);

        if (user.name() != null) {
            instance.setName(user.name());
        }

        if (user.email() != null) {
            instance.setEmail(user.email());
        }

        if (user.password() != null) {
            instance.setPassword(user.password());
        }

        if (user.cpf() != null) {
            instance.setCpf(user.cpf());
        }

        if (user.status() != null) {
            instance.setStatus(user.status());
        }

        userRepository.save(instance);
    }


    @Override
    public void deleteUser(UUID id) {
        User instance = findByIdOrElseThrow(id);
        userRepository.delete(instance);
    }

    public User findByIdOrElseThrow(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
    }

    public void existsByCpfOrEmail(UserRequest user) {
        userRepository.findByCpf(user.cpf()).ifPresent(existingUser -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        });

        userRepository.findByEmail(user.email()).ifPresent(existingUser -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        });
    }

    public User getAuthenticatedUserOrElseThrow() {
        Authentication currentUser = authenticationFacade.getAuthentication();
        UUID userId = UUID.fromString(currentUser.getName());

        return findByIdOrElseThrow(userId);
    }
}
