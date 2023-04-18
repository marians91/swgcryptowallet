package com.swag.crypto.wallet.user.service.impl;

import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.repository.WalletRepository;
import com.swag.crypto.wallet.user.dto.UserDTO;
import com.swag.crypto.wallet.user.entity.Role;
import com.swag.crypto.wallet.user.entity.User;
import com.swag.crypto.wallet.user.repository.RoleRepository;
import com.swag.crypto.wallet.user.repository.UserRepository;
import com.swag.crypto.wallet.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.swag.crypto.wallet.user.Utils.UserUtils.mapDtoToUser;
import static com.swag.crypto.wallet.user.Utils.UserUtils.mapToUserDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final WalletRepository walletRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletRepository = walletRepository;
    }

    @Override
    public void save(UserDTO userDto) {

        User user = mapDtoToUser(userDto);
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

    }

    @Override
    public void delete(String email) {
        User user = userRepository.findByEmail(email);
        Wallet wallet = walletRepository.findByUserId(user.getId()).get();
        wallet.setUser(null);
        walletRepository.save(wallet);
        user.setRoles(null);
        user.setWallet(null);
        user = userRepository.save(user);
        if (user != null) {
            userRepository.delete(user);
        } else {
            //todo throw NotFoundException
        }
    }

    @Override
    public void update(UserDTO userDto, Long id) {
        User user = mapDtoToUser(userDto);

        Optional<Wallet> wallet = walletRepository.findByUserId(id);
        if (wallet.isPresent()) user.setWallet(wallet.get());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
