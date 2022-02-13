package com.sftp.config;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class SecurePassword {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[] { 'S', 'F', 'T', 'P' ,'@', '1', '2', '3','4', 'A', 'E', 'S' ,'T', 'E', 'S', 'T'};

    /**
     * Encrypt a string using AES encryption algorithm.
     *
     * @param pwd the password to be encrypted
     * @return the encrypted string
     */

    public static String encrypt(String pwd) {
        String encodedPwd = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(pwd.getBytes());
            encodedPwd = Base64.getEncoder().encodeToString(encVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedPwd;
    }
    /**
     * Decrypt a string with AES encryption algorithm.
     *
     * @param encryptedData the data to be decrypted
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) {
        String decodedPWD = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
            byte[] decValue = c.doFinal(decordedValue);
            decodedPWD = new String(decValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedPWD;
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey() {
        SecretKeySpec key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

    public static void main(String args[]) {
        String plainPassword = "test";
        System.out.println("Encrypted password is : " + SecurePassword.encrypt(plainPassword));
    }
}
