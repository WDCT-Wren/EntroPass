package Encryption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {
    private SecretKey sharedKey;
    private String sharedPayload;
    @BeforeEach
    void setUp() throws Exception {
        sharedPayload = "password123!";
        sharedKey = PDKF2.deriveKey(
                "masterPassword123!".toCharArray(),
                "C0X75bHo3wiyUgpGO0aCEw==".getBytes(
                ));
    }

    /**
     * Tests the encryption functionality of {@code AES Class} and tests the following assertions:
     * <ul>
     *     <li>If the encrypted text exists</li>
     *     <li>If the encrypted text does not match the initial Payload</li>
     * </ul>
     */
    @Test
    void encryptTest() {
        AES.setKey(sharedKey);
        String originalText = sharedPayload;

        String encryptedPassword = AES.encrypt(originalText);

        assertNotNull(encryptedPassword);
        assertFalse(Arrays.equals(
                originalText.toCharArray(), encryptedPassword.toCharArray()),
                "Encrypted text should not equal to the original text!");
    }

    @Test
    void decryptTest() {
        AES.setKey(sharedKey);
        String originalText = sharedPayload;

        String encryptedPassword = AES.encrypt(originalText);
        String decryptedPassword = AES.decrypt(encryptedPassword);

        assertNotNull(decryptedPassword);
        assertEquals(originalText, decryptedPassword, "Decrypted password must match the original text!");
    }
}