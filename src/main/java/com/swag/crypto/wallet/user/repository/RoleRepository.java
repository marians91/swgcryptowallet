package com.swag.crypto.wallet.user.repository;

import com.swag.crypto.wallet.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
