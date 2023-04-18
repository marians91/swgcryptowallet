package com.swag.crypto.wallet.portfolio.wallet.generator;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class RandomWordsGenerator {

    private static final List<String> WORD_LIST = Arrays.asList(
            "abandon", "ability", "able", "about", "above", "absent",
            "absorb", "abstract", "absurd", "abuse", "access", "accident",
            "account", "accuse", "achieve", "acid", "acoustic", "acquire",
            "across", "act", "action", "actor", "actress", "actual",
            "adapt", "add", "addict", "address", "adjust", "admit",
            "adult", "advance", "advice", "aerobic", "affair", "afford",
            "afraid", "again", "age", "agent", "agree", "ahead"
            // Add more words here if needed
    );

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomWords(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int index = RANDOM.nextInt(WORD_LIST.size());
            sb.append(WORD_LIST.get(index)).append(" ");
        }
        return sb.toString().trim();
    }

}