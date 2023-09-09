package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.util.List;
import java.util.Random;

public class NumberGenerator {
    private static final ISettingsFile testData = new JsonSettingsFile("Test_Data.json");
    private static final Random random = new Random();
    public static int generateNumberOneToNine() {
        int upperBound = 10;
        return random.nextInt(upperBound);
    }

    public static int selectRandomStatusID(int initialId) {
        List<String> statusIds = testData.getList("/status_ids/ids");
        statusIds.remove(String.valueOf(initialId));
        int randomIndex = random.nextInt(statusIds.size());
        String randomStatusId = statusIds.get(randomIndex);
        return Integer.parseInt(randomStatusId);
    }

}