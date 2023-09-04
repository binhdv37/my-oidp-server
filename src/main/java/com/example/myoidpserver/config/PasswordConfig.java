package com.example.myoidpserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class PasswordConfig {

    private String passwordEncryptSecret ="stc123456789!@#$%^&*()!@#$%^&*()stc123456";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(passwordEncryptSecret, 9981, 128);
    }

}
