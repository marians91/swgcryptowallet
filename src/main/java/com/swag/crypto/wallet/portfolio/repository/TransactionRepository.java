package com.swag.crypto.wallet.portfolio.repository;

import com.swag.crypto.wallet.portfolio.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
