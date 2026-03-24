package Encryption;


import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {
    private static SecretKey key;
    private static final int T_LEN = 128;

    public static void setKey(SecretKey key) {
        AES.key = key;
    }

    public static String encrypt(String password) {
        byte[] passwordInByte = password.getBytes();
        byte[] encryptionByte;

        try {
            Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] iv = encryptionCipher.getIV();
            encryptionByte = encryptionCipher.doFinal(passwordInByte);
            
            // Combine IV and encrypted password to prevent duplicate cyphertext if encrypting the same password twice.
            byte[] combined = new byte[iv.length + encryptionByte.length];

            System.arraycopy(iv, 0, combined, 0, iv.length); //inserts the iv bytes to the combined array
            System.arraycopy(encryptionByte, 0, combined, iv.length, encryptionByte.length); //inserts the encryption bytes to the combined array
            
            return encode(combined);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedPassword) {
        
        byte[] combined = decode(encryptedPassword); //encrypted password string to a byte array.
        byte[] decryptedBytes;

        try {
            // Extract IV and encrypted data
            byte[] iv = new byte[12]; // GCM standard IV size
            byte[] encryptedData = new byte[combined.length - iv.length]; //make a new byte array with the size of the encrypted data only (by removing iv size)

            System.arraycopy(combined, 0, iv, 0, iv.length); //Extracts the iv array from the combined array
            System.arraycopy(combined, iv.length, encryptedData, 0, encryptedData.length); //extracts the encrypted data from the combined array
            
            Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(T_LEN, iv);
            decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
            decryptedBytes = decryptionCipher.doFinal(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | InvalidAlgorithmParameterException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(decryptedBytes); //return decrypted bytes into a string form
    }

    private static String encode(byte[] data) {return Base64.getEncoder().encodeToString(data);}
    private static byte[] decode(String data) {return Base64.getDecoder().decode(data);}
}
