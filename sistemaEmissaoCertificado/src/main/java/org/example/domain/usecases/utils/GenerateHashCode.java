package org.example.domain.usecases.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class GenerateHashCode {

    public String genHashCode(String input) throws Exception {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte[] hash = algorithm.digest(input.getBytes(StandardCharsets.UTF_8));

        StringBuilder texto = new StringBuilder();
        for (byte b : hash) {
            texto.append(String.format("%02X", 0xFF & b));
        }
        return texto.toString();
    }

}