package com.swag.crypto.wallet.portfolio.wallet.generator;

import org.bitcoinj.base.Address;
import org.bitcoinj.base.ScriptType;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ECKey;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import java.io.IOException;
import java.security.*;
import java.util.List;

public class KeyGenerator {

    public static ECKey keyPair(List<String> mnemonicSeedPhrase)  {

        // Convert the seed phrase to a deterministic seed
        DeterministicSeed seed = new DeterministicSeed(mnemonicSeedPhrase,null,"",1000000);

        // Create a deterministic key chain from the seed
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();

        // Generate a new private key from the key chain
        ECKey key = keyChain.getKeyByPath(DeterministicKeyChain.BIP44_ACCOUNT_ZERO_PATH, true);
        return key;
    }

    public static String privateKey(ECKey keyPair) {
        NetworkParameters params =  MainNetParams.get();
        return keyPair.getPrivateKeyAsWiF(params.network());
    }
    public static String address(ECKey keyPair) {
        NetworkParameters params =  MainNetParams.get();
        Address address = keyPair.toAddress(ScriptType.P2PKH,params.network());
        return address.toString();
    }

}

