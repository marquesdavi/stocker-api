package com.stocker.api.service.impl;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.entity.Role;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.repository.RoleRepository;
import com.stocker.api.domain.repository.UserRepository;
import com.stocker.api.domain.shared.RequestMapper;
import com.stocker.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RequestMapper<User, UserRequest> requestMapper;

    @Override
    public void createUser(UserRequest user) {
        existsByCpfOrEmail(user);

        Role userRole = roleRepository.findByName(Role.Values.USER.name())
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });

        User instace = requestMapper.toDomain(user);
        instace.setRoles(Set.of(userRole));

        userRepository.save(instace);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public void updateUser(UserRequest user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
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
}
