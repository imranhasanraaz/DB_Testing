package data_access_object;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_transfer_object.TestDTO;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static DatabaseUtils.DatabaseUtils.*;

public class TestDAO {
    private static final ISettingsFile sqlQueryReader = new JsonSettingsFile("Queries.json");

    private TestDTO extractTestDTOFromResultSet(ResultSet resultSet) {
        try {
            return new TestDTO(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("status_id"),
                    resultSet.getString("method_name"),
                    resultSet.getLong("project_id"),
                    resultSet.getLong("session_id"),
                    resultSet.getObject("start_time", LocalDateTime.class),
                    resultSet.getObject("end_time", LocalDateTime.class),
                    resultSet.getString("env"),
                    resultSet.getString("browser"),
                    resultSet.getLong("author_id")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestDTO getTestById(long id) {
        String queryTemplate = sqlQueryReader.getValue("/test_queries/getTestById").toString();
        String query = String.format(queryTemplate, id);
        ResultSet resultSet = executeAndGetResultSet(query);
        try {
            if (resultSet != null && resultSet.next()) {
                return extractTestDTOFromResultSet(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TestDTO> getAllTests() {
        String query = sqlQueryReader.getValue("/test_queries/getAllTests").toString();
        ResultSet resultSet = executeAndGetResultSet(query);
        List<TestDTO> tests = new ArrayList<>();

        try {
            while (resultSet != null && resultSet.next()) {
                tests.add(extractTestDTOFromResultSet(resultSet));
            }
            return tests;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTest(TestDTO test) {

        String queryTemplate = sqlQueryReader.getValue("/test_queries/addTest").toString();
        String query = String.format(queryTemplate, test.getName(), test.getStatus_id(), test.getMethod_name(), test.getProject_id(), test.getSession_id(), test.getStart_time(), test.getEnd_time(), test.getEnv(), test.getBrowser(), test.getAuthor_id());
        Long lastGeneratedId = executeAndGetLastId(query);
        test.setId(lastGeneratedId);
    }

    public void updateTest(TestDTO test) {
        String queryTemplate = sqlQueryReader.getValue("/test_queries/updateTest").toString();
        String query = String.format(queryTemplate, test.getName(), test.getStatus_id(), test.getMethod_name(), test.getProject_id(), test.getSession_id(), test.getStart_time(), test.getEnd_time(), test.getEnv(), test.getBrowser(), test.getAuthor_id(), test.getId());
        executeScalar(query);

    }

    public void deleteTest(long id) {
        String queryTemplate = sqlQueryReader.getValue("/test_queries/deleteTest").toString();
        String query = String.format(queryTemplate, id);
        executeScalar(query);
    }

    public List<TestDTO> getTestWithRepeatingDigit(int digit, int limit) {
        List<TestDTO> testWithSameDigits = new ArrayList<>();
        String queryTemplate = sqlQueryReader.getValue("/test_queries/getTestsWithTwoRepeatingDigitsInId").toString();
        String query = String.format(queryTemplate, digit + String.valueOf(digit), limit);
        ResultSet resultSet = executeAndGetResultSet(query);

        try {
            while (resultSet != null && resultSet.next()) {
                testWithSameDigits.add(extractTestDTOFromResultSet(resultSet));
            }
            return testWithSameDigits;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isRecordExistsInDb(long id) {
        List<TestDTO> tests = getAllTests();
        for (TestDTO test : tests
        ) {
            if (test.getId().equals(id))
                return true;
        }
        return false;
    }
}