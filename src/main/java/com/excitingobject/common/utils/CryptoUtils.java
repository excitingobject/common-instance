package com.excitingobject.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtils {

    /**
     * AES256
     */
    private static final String ENCODING = "UTF-8";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    private static Cipher getCipherAES256(int mode, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] keyBytes = new byte[16];
        byte[] bEncryptKey = key.getBytes(ENCODING);
        int len = bEncryptKey.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(bEncryptKey, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(mode, keySpec, ivSpec);
        return cipher;
    }

    public static String encryptAES256(String encryptText, String key) throws Exception {
        Cipher cipher = getCipherAES256(Cipher.ENCRYPT_MODE, key);
        byte[] src = cipher.doFinal(encryptText.getBytes(ENCODING));
        byte[] enBytes = Base64.getEncoder().encode(src);
        String result = new String(enBytes, ENCODING);
        return result;
    }

    public static String decryptAES256(String decryptText, String key) throws Exception {
        Cipher cipher = getCipherAES256(Cipher.DECRYPT_MODE, key);
        byte[] deBytes = Base64.getDecoder().decode(decryptText.getBytes(ENCODING));
        byte[] src = cipher.doFinal(deBytes);
        String result = new String(src, ENCODING);
        return result;
    }
}
