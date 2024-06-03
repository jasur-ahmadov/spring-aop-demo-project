package com.company.service.impl;

import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.exception.UserAlreadyExistsException;
import com.company.exception.UserNotFoundException;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserInter;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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

    @CacheEvict(value = "users:id", key = "#id")
    public void clearCache(Long id) {
        System.err.println("Clearing hash key");
    }

    @Scheduled(fixedRate = 10_000)
    @CacheEvict(value = "users:id", allEntries = true)
    public void clearCache() {
        System.err.println("Clearing all entries per 10 seconds");
    }

    @Override
    @CacheEvict(value = "users", key = "#user.id")
    public void updateUser(User user) {
        System.err.println("Inside updateUser method");
        Optional.ofNullable(user.getId())
                .flatMap(userRepository::findById)
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + user.getId()));
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