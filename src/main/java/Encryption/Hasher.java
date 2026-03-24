package Encryption;

import org.mindrot.jbcrypt.BCrypt;

public class Hasher {
    private final static String salt = BCrypt.gensalt(12);

    public static String hashPassword(String password) {return BCrypt.hashpw(password, salt);}
}
