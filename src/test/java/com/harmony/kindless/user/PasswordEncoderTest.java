package com.harmony.kindless.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wuxii
 */
public class PasswordEncoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("abc123");
        boolean matches = passwordEncoder.matches("abc123", password);
        System.out.println(matches);
        System.out.println(password);
    }

}
