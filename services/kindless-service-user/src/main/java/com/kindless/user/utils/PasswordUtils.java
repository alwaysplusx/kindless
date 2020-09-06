package com.kindless.user.utils;

import java.util.Objects;

/**
 * @author wuxin
 */
public class PasswordUtils {

    public static boolean isMatched(String password, String hashedPassword) {
        return Objects.equals(password, hashedPassword);
    }

}
