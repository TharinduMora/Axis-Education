package com.teamtrace.axiseducation.util.password;

import java.security.GeneralSecurityException;

public interface PasswordHandler {
    short PBKDF2 = 1;
    short MD5 = 2;

    String hashPassword(final String username, final String password) throws GeneralSecurityException;

    boolean validatePassword(final String username, final String password, final String storedHash) throws GeneralSecurityException;
}
