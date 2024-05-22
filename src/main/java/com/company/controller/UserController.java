package com.company.controller;

import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.service.UserInter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserInter userInter;

    @GetMapping
    public List<UserDTO> getAll() {
        return userInter.getAll();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userInter.getById(id);
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        userInter.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userInter.deleteUserById(id);
    }
}