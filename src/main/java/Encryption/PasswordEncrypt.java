package Encryption;


import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncrypt {
    private static SecretKey key;
    private static final int KEY_LENGTH = 128;
    private static final int T_LEN = 128;

    static {
        // Initialize the key when the class is loaded
        init();
    }

    private static void init() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_LENGTH);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String password) {
        if (key == null) {
            init();
        }
        
        byte[] passwordInByte = password.getBytes();
        byte[] encryptionByte;

        try {
            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] iv = encryptionCipher.getIV();
            encryptionByte = encryptionCipher.doFinal(passwordInByte);
            
            // Combine IV and encrypted data
            byte[] combined = new byte[iv.length + encryptionByte.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptionByte, 0, combined, iv.length, encryptionByte.length);
            
            return encode(combined);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedPassword) {
        if (key == null) {
            init();
        }
        
        byte[] combined = decode(encryptedPassword);
        byte[] decryptedBytes;

        try {
            // Extract IV and encrypted data
            byte[] iv = new byte[12]; // GCM standard IV size
            byte[] encryptedData = new byte[combined.length - iv.length];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, encryptedData, 0, encryptedData.length);
            
            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
            decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
            decryptedBytes = decryptionCipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(decryptedBytes);
    }

    private static String encode(byte[] data) {return Base64.getEncoder().encodeToString(data);}
    private static byte[] decode(String data) {return Base64.getDecoder().decode(data);}
}
