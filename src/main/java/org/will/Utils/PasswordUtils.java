package org.will.Utils;


import java.util.Base64;

public class PasswordUtils {

    public static String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static boolean matches(String password, String encodedPassword) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(encodedPassword)) {
            return false;
        }
        return encodedPassword.equals(encode(password));
    }
}
