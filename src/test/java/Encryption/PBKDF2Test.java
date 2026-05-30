package Encryption;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class PBKDF2Test {

    @Test
    void deriveKeyIsDeterministic() throws Exception{
        byte[] salt = Base64.getDecoder().decode(PBKDF2.getSalt());

        SecretKey key1 = PBKDF2.deriveKey("Password123!".toCharArray(), salt);
        SecretKey key2 = PBKDF2.deriveKey("Password123!".toCharArray(), salt);

        assertNotEquals(key1, key2,
                "Both keys generated with the same salt and password should match!"
        );
    }

    @Test
    void deriveKeyTest() throws Exception {
        byte[] salt1 = Base64.getDecoder().decode(PBKDF2.getSalt());
        byte[] salt2 = Base64.getDecoder().decode(PBKDF2.getSalt());

        SecretKey key1 = PBKDF2.deriveKey("Password123!".toCharArray(), salt1);
        SecretKey key2 = PBKDF2.deriveKey("Password123!".toCharArray(), salt2);
        SecretKey key3 = PBKDF2.deriveKey("differentPassword123!".toCharArray(), salt1);

        assertNotEquals(key1, key2,
                "Keys derived from different salts must produce different keys!");
        assertNotEquals(key1, key3,
                "Keys derived from different passwords must produce different keys!");
    }

    @Test
    void getSalt() {
        String salt1 = PBKDF2.getSalt();
        String salt2 = PBKDF2.getSalt();

        assertNotEquals(salt1, salt2,
                "two salt strings generated must not match with one another!");
    }
}