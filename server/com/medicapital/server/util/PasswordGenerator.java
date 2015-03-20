package com.medicapital.server.util;

import java.util.Random;

public class PasswordGenerator {

    private static final int PASSWORD_LENGHT = 12;
    private static final Random random = new Random();
    private static final char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 't', 'w', 'z', 'x', 'y', '1', '2', '3', '4', '5', '6', '7', '8', '9', '@', '#', '$', '%', '!' };

    public String generate() {
        synchronized (random) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < PASSWORD_LENGHT; i++) {
                int charNumber = random.nextInt(chars.length);
                password.append(chars[charNumber]);
            }
            return password.toString();
        }
    }

}
