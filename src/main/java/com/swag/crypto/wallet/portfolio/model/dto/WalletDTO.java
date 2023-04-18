package com.swag.crypto.wallet.portfolio.model.dto;


import com.swag.crypto.wallet.portfolio.model.bean.Crypto;
import com.swag.crypto.wallet.user.dto.UserDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDTO {
    private Long id;
    private UserDTO user;
    private Double currencyAmount;
    private Double btcAmount;
    private String assetName;
    private String code;
    private String address;
    private Crypto crypto;
}
