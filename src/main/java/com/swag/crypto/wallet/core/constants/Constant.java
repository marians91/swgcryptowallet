package com.swag.crypto.wallet.core.constants;

public class Constant {

    public static class ErrorCode{
        public final static int SERVER_ERROR = 500;
    }
    public static class ErrorMessage{
        public final static String UNEXPECTED_ERROR = "Sorry. Unexpected error occurred.";
    }

    public static class Path{
        public final static String TRANSACTION = "/transaction";
        public final static String USER = "/user";
        public final static String WALLET = "/wallet";
    }


}
