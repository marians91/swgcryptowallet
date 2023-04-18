package com.swag.crypto.wallet.user.service;

import com.swag.crypto.wallet.user.dto.UserDTO;
import com.swag.crypto.wallet.user.entity.User;

import java.util.List;

public interface UserService {
    void save(UserDTO userDto);

    void delete(String email);

    void update(UserDTO userDto, Long id);

    User findById(Long id);

    User findByEmail(String email);

    List<UserDTO> findAllUsers();
}