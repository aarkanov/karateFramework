package slalom.karate.framework;

import java.util.UUID;

public class RandomGeneratorUtility {
    public static double generateRandomNumber(int max) {
        return Math.floor(Math.random() * max);
    }

    public static UUID generateUUID() {
        return java.util.UUID.randomUUID();
    }
}