package com.harmony.kindless.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wuxii
 */
public class PasswordEncoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("vQGEuGJGDFX2");
        System.out.println(password);
    }

}
