package Encryption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {
    private SecretKey sharedKey;
    private String sharedPayload;

    @BeforeEach
    void setUp() throws Exception {
        byte[] salt = Base64.getDecoder().decode(PBKDF2.getSalt());
        sharedPayload = "password123!";
        sharedKey = PBKDF2.deriveKey(
                "masterPassword123!".toCharArray(), salt
                );
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

        char[] encrypted1 = AES.encrypt(originalText).toCharArray();
        char[] encrypted2 = AES.encrypt(originalText).toCharArray();

        assertNotNull(encrypted1);
        assertFalse(Arrays.equals(
                originalText.toCharArray(), encrypted1),
                "Encrypted text should not equal to the original text!");
        assertFalse(Arrays.equals(encrypted1, encrypted2),
                "Each encryption should make a completely different ciphertext!");
    }

    @Test
    void decryptTest() {
        AES.setKey(sharedKey);

        String encrypted = AES.encrypt(sharedPayload);
        String decrypted = AES.decrypt(encrypted);

        assertNotNull(decrypted);
        assertEquals(sharedPayload, decrypted, "Decrypted password must match the original text!");
    }
}