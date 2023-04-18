package com.swag.crypto.wallet.portfolio.wallet.encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static byte[] sha256ripemd160(byte[] data) throws NoSuchAlgorithmException {
        // Apply SHA256 hash function
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] sha256Hash = sha256.digest(data);

        // Apply RIPEMD160 hash function
        MessageDigest ripemd160 = MessageDigest.getInstance("RIPEMD160");
        byte[] ripemd160Hash = ripemd160.digest(sha256Hash);

        return ripemd160Hash;
    }

}
