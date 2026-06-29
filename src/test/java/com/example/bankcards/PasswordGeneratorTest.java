package com.example.bankcards;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorTest {

    @Test
    public void generatePassword(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin";
        String crypt = encoder.encode(password);
        System.out.println(crypt);
    }
}
