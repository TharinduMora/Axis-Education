package com.teamtrace.axiseducation.util;

import java.util.UUID;

public class UUIDUtil {
    private UUIDUtil() {
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomPassword(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }
}
