package Tests;

import data_transfer_object.TestDTO;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static test_utils.RandomStatusID.selectRandomStatusID;
import static utils.NumberGenerator.generateNumberOneToNine;

public class TestOfProcessingData extends BaseTest {
    protected List<TestDTO> testWithRepeatingDigit = new ArrayList<>();
    protected List<Long> initialTestIDs = new ArrayList<>();
    protected List<Long> updatedTestIDs = new ArrayList<>();
    protected TestDTO test;

    @BeforeMethod
    public void copiedTestWithRepeatingDigit() {
        logger.info("Select tests from the database where ID contains two random repeating digits");
        testWithRepeatingDigit = testDAO.getTestWithRepeatingDigit(generateNumberOneToNine(), Integer.parseInt(testData.getValue("/test_count/limit").toString()));
        logger.info("Copy these tests with an indication of the current project and the author.");
        for (TestDTO test : testWithRepeatingDigit) {
            test.setAuthor_id(author.getId());
            test.setProject_id(project.getId());
            initialTestIDs.add(test.getId());
            testDAO.addTest(test);
            updatedTestIDs.add(test.getId());
        }
    }

    @Test
    public void testOfProcessingData() {

        logger.info("Simulate the launch of each tests by updating status id");
        for (Long testId : updatedTestIDs) {
            test = testDAO.getTestById(testId);
            int initialStatusId = test.getStatus_id();
            test.setStatus_id(selectRandomStatusID(initialStatusId));
            testDAO.updateTest(test);
        }

        logger.info("Compare with Updated test and Initially selected Test by checking status id is sam or not");
        for (int i = 0; i < updatedTestIDs.size(); i++) {
            Assert.assertNotEquals(testDAO.getTestById(updatedTestIDs.get(i)).getStatus_id(), testDAO.getTestById(initialTestIDs.get(i)).getStatus_id(), "Tests are not completed, information is not updated.");
        }
    }

    @AfterClass
    public void AfterClass() {
        for (Long testId : updatedTestIDs) {
            testDAO.deleteTest(testId);
        }
        authorDAO.deleteAuthor(author.getId());
        projectDAO.deleteProject(project.getId());
        logger.info("Deleted all updated information");
    }
}