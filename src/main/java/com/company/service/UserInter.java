package com.company.service;

import com.company.dto.UserDTO;
import com.company.entity.User;

import java.util.List;

public interface UserInter {

    List<UserDTO> getAll();

    UserDTO getById(Long id);

    User saveUser(User user);

    void deleteUserById(Long id);
}