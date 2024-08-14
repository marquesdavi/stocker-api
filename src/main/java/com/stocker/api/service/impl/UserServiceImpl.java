package com.stocker.api.service.impl;

import com.stocker.api.domain.dto.user.UserRequest;
import com.stocker.api.domain.entity.User;
import com.stocker.api.domain.mapper.UserMapper;
import com.stocker.api.domain.repository.UserRepository;
import com.stocker.api.domain.shared.RequestMapper;
import com.stocker.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RequestMapper<User, UserRequest> requestMapper;

    @Override
    public void createUser(UserRequest user) {
        existsByCpf(user.cpf());

        User instace = requestMapper.toDomain(user);

        userRepository.save(instace);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    public void existsByCpf(String cpf){
        if(userRepository.findByCpf(cpf).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
