package com.swag.crypto.wallet;

import com.swag.crypto.wallet.portfolio.connector.ExternalConnector;
import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.repository.WalletRepository;
import com.swag.crypto.wallet.portfolio.repository.mapper.WalletMapper;
import com.swag.crypto.wallet.portfolio.service.WalletService;
import com.swag.crypto.wallet.user.entity.Role;
import com.swag.crypto.wallet.user.entity.User;
import com.swag.crypto.wallet.user.repository.RoleRepository;
import com.swag.crypto.wallet.user.repository.UserRepository;

import static com.swag.crypto.wallet.user.Utils.UserUtils.mapToUserDto;

import com.swag.crypto.wallet.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
@Slf4j
class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    WalletService walletService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        //createNoWalletUser();
        //bootstrapData();
        //importWallet();

        // realTimeInf();

        log.info("Application Started!");
    }

    public void bootstrapData() {

        final String ADMIN_ROLE = "ROLE_ADMIN";
        final String USER_ROLE = "ROLE_USER";

        Role adminRole = new Role();
        adminRole.setName(ADMIN_ROLE);

        Role userRole = new Role();
        userRole.setName(USER_ROLE);

        Role adminRolePersisted = roleRepository.findByName(ADMIN_ROLE);
        Role userRolePersisted = roleRepository.findByName(USER_ROLE);

        if (userRolePersisted == null) {
            userRole = roleRepository.save(userRole);
        } else {
            userRole = userRolePersisted;
        }
        if (adminRolePersisted == null) {
            adminRole = roleRepository.save(adminRole);
        } else {
            adminRole = adminRolePersisted;
        }


        User userAdmin = User
                .builder()
                .email("mariano.d@live.it")
                .password(passwordEncoder.encode("password"))
                .firstName("Mariano")
                .lastName("Depau")
                .fullName("Mariano Depau")
                //  .roles(Arrays.asList(adminRole, userRole))
                .build();


        log.info("user admin : {}", userAdmin);
        User user = User
                .builder()
                .email("carlo.d@live.it")
                .password(passwordEncoder.encode("password"))
                .firstName("Carlo")
                .lastName("Carli")
                .fullName("Carlo Carlo")
                //  .roles(Arrays.asList(userRole))
                .build();
        log.info("user : {}", user);

        User savedAdmin = userRepository.findByEmail(userAdmin.getEmail());
        User savedUser = userRepository.findByEmail(user.getEmail());

        if (savedUser == null) {

            savedUser = userRepository.save(user);
            savedUser.setRoles(Arrays.asList(userRole));
            savedUser = userRepository.save(user);
        }
        if (savedAdmin == null) {

            savedAdmin = userRepository.save(userAdmin);
            savedAdmin.setRoles(Arrays.asList(userRole, adminRole));
            savedAdmin = userRepository.save(userAdmin);
        }

        log.info("user admin saved : {}", savedAdmin);
        log.info("user  saved : {}", savedUser);

        List<String> mnemonicSeedPhrase = List.of("abandon", "amount", "power", "mutual", "admin", "token", "wild", "jar", "crouch", "twice", "announce", "pudding");
        List<String> mnemonicSeedPhrase1 = List.of("abandon", "amount", "powerING", "mutual", "admin", "token", "wild", "jar", "crouch", "twice", "announce", "pudding");
        List<String> mnemonicSeedPhrase2 = List.of("abandon", "XXXXX", "powerING", "mutual", "admin", "token", "wild", "jar", "crouch", "twice", "announce", "pudding");


        Wallet walletAdmin = WalletMapper.INSTANCE.walletDtoToWallet(walletService.create(mnemonicSeedPhrase, mapToUserDto(savedAdmin)));

        Wallet walletUser = WalletMapper.INSTANCE.walletDtoToWallet(walletService.create(mnemonicSeedPhrase1, mapToUserDto(savedUser)));


    }

    public void createNoWalletUser() {
        final String USER_ROLE = "ROLE_USER";
        Role userRole = new Role();
        userRole.setName(USER_ROLE);

        Role adminRolePersisted = roleRepository.findByName(USER_ROLE);

        if (adminRolePersisted == null) adminRolePersisted = roleRepository.save(userRole);
        User user = User
                .builder()
                .email("rossi.d@live.it")
                .password(passwordEncoder.encode("password"))
                .firstName("Paolo")
                .lastName("Rossi")
                .fullName("Paolo Rossi")
                //  .roles(Arrays.asList(adminRole, userRole))
                .build();

        user = userRepository.save(user);
        user.setRoles(Arrays.asList(adminRolePersisted));
        user = userRepository.save(user);

    }

    public void importWallet() {
        final String ADMIN_ROLE = "ROLE_USER";
        Role adminRolePersisted = roleRepository.findByName(ADMIN_ROLE);

        User user = User
                .builder()
                .email("rossi.d@live.it")
                .password("password")
                .firstName("Paolo")
                .lastName("Rossi")
                .fullName("Paolo Rossi")
                //  .roles(Arrays.asList(adminRole, userRole))
                .build();

        user = userRepository.save(user);
        user.setRoles(Arrays.asList(adminRolePersisted));
        user = userRepository.save(user);
        List<String> mnemonicSeedPhrase = List.of("abandon", "amount", "power", "mutual", "admin", "token", "wild", "jar", "crouch", "twice", "announce", "pudding");

        WalletDTO wallet = walletService.importWallet(mnemonicSeedPhrase, mapToUserDto(user));
        System.out.println(wallet);
    }

    public void realTimeInfo() {
        ExternalConnector.btcRTdata();

    }
}