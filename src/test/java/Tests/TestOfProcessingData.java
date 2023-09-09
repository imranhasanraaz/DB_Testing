package Tests;

import data_transfer_object.TestDTO;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.NumberGenerator.generateNumberOneToNine;
import static utils.NumberGenerator.selectRandomStatusID;

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
            Assert.assertEquals(project.getId(), projectDAO.getProjectById(project.getId()).getId(), "Project info not updated");
            Assert.assertEquals(author.getId(), authorDAO.getAuthorById(author.getId()).getId(), "Author info not updated");
        }
    }

    @Test
    public void testOfProcessingData() {

        logger.info("Simulate the launch of each tests by updating status id and Checking that the Status ID is successfully updated or not");
        for (Long testId : updatedTestIDs) {
            test = testDAO.getTestById(testId);
            int initialStatusId = test.getStatus_id();
            int updatedStatusID = selectRandomStatusID(initialStatusId);
            test.setStatus_id(updatedStatusID);
            testDAO.updateTest(test);
            Assert.assertEquals(updatedStatusID, testDAO.getTestById(testId).getStatus_id(), "Status id is not updated");
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