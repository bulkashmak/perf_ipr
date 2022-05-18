package ru.bulkashmak.Util;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Util {

    public static String generateRandomString(int bound) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(bound)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
