package com.swag.crypto.wallet.portfolio.wallet.generator;

import com.swag.crypto.wallet.portfolio.entity.Wallet;
import com.swag.crypto.wallet.portfolio.enumeration.Crypto;

public class BitcoinWalletGenerator {

    public static Wallet generateWallet()  {
        // set initial amount to 10000
        Wallet wallet =
                Wallet.
                        builder()
                        .btcAmount(10000.0)
                        .assetName(Crypto.BTC.getName())
                        .code(Crypto.BTC.getCode())
                        .build();

        return wallet;
    }

}