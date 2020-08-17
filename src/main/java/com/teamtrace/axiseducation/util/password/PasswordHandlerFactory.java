package com.teamtrace.axiseducation.util.password;

public class PasswordHandlerFactory {

    public static PasswordHandler getPasswordHandler(final short type) {
        switch (type) {
            case PasswordHandler.MD5: {
                return MD5PasswordHandler.getInstance();
            }
            case PasswordHandler.PBKDF2: {
                return PBKDF2PasswordHandler.getInstance();
            }
            default: {
                return PBKDF2PasswordHandler.getInstance();
            }
        }
    }
}
