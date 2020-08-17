package com.teamtrace.axiseducation.util.password;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class MD5PasswordHandler implements PasswordHandler {
    private static final MD5PasswordHandler MD_5_PASSWORD_HANDLER = new MD5PasswordHandler();

    private MD5PasswordHandler() {
    }

    public static MD5PasswordHandler getInstance() {
        return MD_5_PASSWORD_HANDLER;
    }

    @Override
    public String hashPassword(String username, String password) throws GeneralSecurityException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }

    @Override
    public boolean validatePassword(String username, String password, String storedHash) throws GeneralSecurityException {
        String hash = hashPassword(username, password);
        return storedHash.equals(hash);
    }
}
