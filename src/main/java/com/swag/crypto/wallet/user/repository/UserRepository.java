package com.swag.crypto.wallet.user.repository;

import com.swag.crypto.wallet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);


}
