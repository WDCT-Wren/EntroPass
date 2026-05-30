package Encryption;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * PBKDF2 implementation used to derive the AES key from the master password.
 */
public class PBKDF2 {
    /**
     * Derives the AES key from the master password and its stored salt.
     * @param masterPassword master password as a char array
     * @param salt salt as a byte array
     * @return the derived AES key
     * @throws Exception if the key derivation fails
     */
    public static SecretKey deriveKey(char[] masterPassword, byte[] salt) throws Exception {
        byte[] keyBytes;
        PBEKeySpec spec = new PBEKeySpec(
                masterPassword,
                salt,
                310_000,
                128 // key length
        );

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            keyBytes = factory.generateSecret(spec).getEncoded();
        }
        finally {
            spec.clearPassword(); //wipe the password from memory
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Private method that generates the random salt of 16 bytes.
     * @return the salt as a byte array
     */
    private static byte[] generateSalt() {
        byte[] salt = new byte[16];

        //Generate a random salt and inserts it into the byte array
        new SecureRandom().nextBytes(salt);

        return salt;
    }

    /**
     * getter method that returns the salt as a string.
     * @return the salt as a string
     */
    public static String getSalt() {
        byte[] salt = generateSalt();
        return Base64.getEncoder().encodeToString(salt);
    }
}
