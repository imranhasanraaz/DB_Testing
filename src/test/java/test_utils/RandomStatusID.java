package test_utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.util.List;
import java.util.Random;


public class RandomStatusID {
    private static final ISettingsFile testData = new JsonSettingsFile("Test_Data.json");

    public static int selectRandomStatusID(int initialId) {
        List<String> statusIds = testData.getList("/status_ids/ids");
        statusIds.remove(String.valueOf(initialId));
        Random random = new Random();
        int randomIndex = random.nextInt(statusIds.size());
        String randomStatusId = statusIds.get(randomIndex);
        return Integer.parseInt(randomStatusId);
    }
}