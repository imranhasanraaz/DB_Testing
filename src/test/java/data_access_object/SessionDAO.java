package data_access_object;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_transfer_object.SessionDTO;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static DatabaseUtils.DatabaseUtils.*;

public class SessionDAO {
    private final ISettingsFile sqlQueryReader = new JsonSettingsFile("Queries.json");

    public SessionDTO getSessionById(long id) {
        try {
            String queryTemplate = sqlQueryReader.getValue("/session_queries/getSessionById").toString();
            String query = String.format(queryTemplate, id);
            ResultSet resultSet = executeAndGetResultSet(query);

            if (resultSet != null && resultSet.next()) {
                return new SessionDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("session_key"),
                        resultSet.getObject("created_time", LocalDateTime.class),
                        resultSet.getLong("build_number")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SessionDTO> getAllSessions() {

        String query = sqlQueryReader.getValue("/session_queries/getAllSessions").toString();
        ResultSet resultSet = executeAndGetResultSet(query);
        List<SessionDTO> sessions = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                sessions.add(new SessionDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("session_key"),
                        resultSet.getObject("created_time", LocalDateTime.class),
                        resultSet.getLong("build_number")
                ));
            }
            return sessions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSession(SessionDTO session) {
        String queryTemplate = sqlQueryReader.getValue("/session_queries/addSession").toString();
        String query = String.format(queryTemplate, session.getSession_key(), session.getCreated_time(), session.getBuild_number());
        Long lastGeneratedId = executeAndGetLastId(query);
        session.setId(lastGeneratedId);


    }

    public void updateSession(SessionDTO session) {
        String queryTemplate = sqlQueryReader.getValue("/session_queries/updateSession").toString();
        String query = String.format(queryTemplate, session.getSession_key(), session.getCreated_time(), session.getBuild_number(), session.getId());
        executeScalar(query);
    }

    public void deleteSession(long id) {
        String queryTemplate = sqlQueryReader.getValue("/session_queries/deleteSession").toString();
        String query = String.format(queryTemplate, id);
        executeScalar(query);
    }

    public boolean isRecordExistsInDb(long id) {
        List<SessionDTO> sessions = getAllSessions();
        for (SessionDTO session : sessions) {
            if (session.getId().equals(id))
                return true;
        }
        return false;
    }
}