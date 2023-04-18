package com.swag.crypto.wallet.core.constants;

public class Constant {

    public static class ErrorCode{
        public final static int SERVER_ERROR = 500;

    }
    public static class ErrorMessage{
        public final static String UNEXPECTED_ERROR = "Sorry. Unexpected error occurred.";
        public final static String IMPORT_PHASE = "IMPORT_PHASE";
        public final static String TRANSFER_FUNDS_PHASE = "TRANSFER_FOUND_PHASE";
    }

    public static class Path{
        public final static String TRANSACTION = "/transaction";
        public final static String USER = "/user";
        public final static String WALLET = "/wallet";
    }


}
