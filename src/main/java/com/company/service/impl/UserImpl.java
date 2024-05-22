package com.company.service.impl;

import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.exception.UserAlreadyExistsException;
import com.company.exception.UserNotFoundException;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserInter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserImpl implements UserInter {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Cacheable(value = "users")
    @Override
    public List<UserDTO> getAll() {
        System.err.println("Inside getAll method");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "users:id", key = "#id")
    @Override
    public UserDTO getById(Long id) {
        System.err.println("Inside getById method");
        return userRepository.findById(id)
                .stream()
                .map(userMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User saveUser(User user) {
        System.err.println("Inside saveUser method");
        var existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists with the given ID: " + user.getId());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        System.err.println("Inside deleteUserById method");
        if (id != null && userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}