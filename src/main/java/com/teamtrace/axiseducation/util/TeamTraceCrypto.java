package com.teamtrace.axiseducation.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class TeamTraceCrypto {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static String encrypt(String strToEncrypt) {
        try {
            setKey(getKey());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            setKey(getKey());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            for (String arg : args) {
                System.out.println(arg + " = " + TeamTraceCrypto.encrypt(arg));
            }
        } else {
            System.out.println("Nothing to display.");
        }

    }

    private static final String getKey() {
        StringBuilder sb = new StringBuilder(16);
        sb.append('G');
        sb.append('B');
        sb.append('t');
        sb.append('l');
        sb.append('E');
        sb.append('p');
        sb.append('w');
        sb.append('@');
        sb.append('1');
        sb.append('0');
        sb.append('6');
        sb.append('4');
        sb.append('8');
        sb.append('2');
        sb.append('5');
        sb.append('9');

        return sb.toString();
    }

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
