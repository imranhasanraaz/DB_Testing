package utils;

import java.util.Random;

public class NumberGenerator {
    public static int generateNumberOneToNine() {
        Random random = new Random();
        int upperBound = 10;
        return random.nextInt(upperBound);
    }
}