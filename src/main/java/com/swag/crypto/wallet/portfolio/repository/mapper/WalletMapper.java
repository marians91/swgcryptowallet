package com.swag.crypto.wallet.portfolio.repository.mapper;


import com.swag.crypto.wallet.portfolio.model.dto.WalletDTO;
import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.user.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface WalletMapper {
    static WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "currencyAmount", target = "currencyAmount")
    @Mapping(source = "btcAmount", target = "btcAmount")
    @Mapping(source = "assetName", target = "assetName")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "user.roles",target = "user.roles", ignore = true)
    Wallet walletDtoToWallet(WalletDTO walletDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "currencyAmount", target = "currencyAmount")
    @Mapping(source = "btcAmount", target = "btcAmount")
    @Mapping(source = "assetName", target = "assetName")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "user.roles", target = "user.roles" , qualifiedByName = "rolesToString")
    @Mapping(source = "user", target = "user")
    WalletDTO walletToWallweDto(Wallet Wallet);


    @Named("rolesToString")
    default List<String> roleToString(List<Role> roles) {
        List<String> names = roles.stream().map(role -> role.getName()).collect(Collectors.toList());
        return names;
    }
    @Named("isAdmin")
    default boolean isAdmin(List<Role> roles) {
        List<String> names = roles.stream().filter(r -> r.getName().equals("ROLE_ADMIN")).map(role -> role.getName()).collect(Collectors.toList());
        return !names.isEmpty();
    }

}
