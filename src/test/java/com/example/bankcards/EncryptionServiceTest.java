package com.example.bankcards;

import com.example.bankcards.util.EncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptionServiceTest {

    @Test
    public void encryptionCard() {
        String key = EncryptionService.generateKey();
        System.out.println(key);
    }
}
