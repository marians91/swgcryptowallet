package com.swag.crypto.wallet.portfolio.model.bean;

import com.swag.crypto.wallet.user.dto.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InitWallet {
    UserDTO user;
    String mnemonicSeedPhrase;
    String typeOfOperation;


}
