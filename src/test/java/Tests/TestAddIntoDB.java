package Tests;

import data_transfer_object.TestDTO;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

public class TestAddIntoDB extends BaseTest {

    @Test
    public void testConcatenateStrings() {
        logger.info("Running Random Test");
        String actual = "hello " + "world";
        String expected = "hello world";
        assertEquals(actual, expected, "Concatenation of strings with spaces is not correct");
    }

    @AfterMethod
    protected void afterMethod(ITestResult result) {
        if (result.isSuccess()) {
            status_id = 1;
        } else if (result.wasRetried()) {
            status_id = 3;
        } else {
            status_id = 2;
        }
        int nanosToRemove = 0;
        session = sessionDAO.getSessionById(Long.parseLong(testData.getValue("/session/session_id").toString()));
        logger.info("Add a result of each completed test in the database as a new record in the Test table.");
        testDAO.addTest(test = new TestDTO(
                id,
                result.getTestClass().getName(),
                status_id,
                result.getMethod().getMethodName(),
                project.getId(),
                session.getId(),
                start_time.withNano(nanosToRemove),
                LocalDateTime.now().withNano(nanosToRemove),
                testData.getValue("/project_details/env").toString(),
                testData.getValue("/project_details/browser").toString(),
                author.getId()
        ));
    }

    @AfterClass
    public void deleteAllUpdatedInformation() {
        Assert.assertEquals(test, testDAO.getTestById(test.getId()), "Uploaded test data is not correct");
        if (testDAO.isRecordExistsInDb(test.getId())) {
            testDAO.deleteTest(test.getId());
        }
        authorDAO.deleteAuthor(author.getId());
        projectDAO.deleteProject(project.getId());
        logger.info("Deleted all updated information");
    }
}