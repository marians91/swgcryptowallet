package com.swag.crypto.wallet.portfolio.repository.mapper;

import com.swag.crypto.wallet.portfolio.model.dto.TransactionDTO;
import com.swag.crypto.wallet.portfolio.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface TransactionMapper {

    static TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction dtoToEntity(TransactionDTO transactionDto);
    TransactionDTO transactionToTransactionDto(Transaction transaction);
}
