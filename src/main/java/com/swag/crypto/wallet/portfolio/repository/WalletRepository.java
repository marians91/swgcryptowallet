package com.swag.crypto.wallet.portfolio.repository;

import com.swag.crypto.wallet.portfolio.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long id);

    Optional<Wallet> findByAddress(String address);
    @Modifying
    @Query("update Wallet w set w.btcAmount = :amount where w.address = :address")
    void updateWalletAmount(@Param("amount") Double amount,@Param("address") String address);
}
