package Encryption;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PDKF2 {
    private static final byte[] salt = "234567asdbd".getBytes(); //TODO: Replace with DB Salt

    public static SecretKey deriveKey(char[] masterPassword) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(
                masterPassword,
                salt,
                310_000,
                128 // key length
        );

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        spec.clearPassword(); //wipe the password from memory

        return new SecretKeySpec(keyBytes, "AES");
    }
}
