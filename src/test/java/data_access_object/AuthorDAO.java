package data_access_object;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_transfer_object.AuthorDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static DatabaseUtils.DatabaseUtils.*;


public class AuthorDAO {
    private final ISettingsFile sqlQueryReader = new JsonSettingsFile("Queries.json");

    public AuthorDTO getAuthorById(long id) {
        String queryTemplate = sqlQueryReader.getValue("/author_queries/getAuthorById").toString();
        String query = String.format(queryTemplate, id);
        ResultSet resultSet = executeAndGetResultSet(query);

        try {
            if (resultSet != null && resultSet.next()) {
                return new AuthorDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<AuthorDTO> getAllAuthors() {
        String query = sqlQueryReader.getValue("/author_queries/getAllAuthors").toString();
        ResultSet resultSet = executeAndGetResultSet(query);
        List<AuthorDTO> authors = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                authors.add(new AuthorDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("email"))
                );
            }
            return authors;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addAuthor(AuthorDTO author) {
        String queryTemplate = sqlQueryReader.getValue("/author_queries/addAuthor").toString();
        String query = String.format(queryTemplate, author.getName(), author.getLogin(), author.getEmail());
        Long lastGeneratedId = executeAndGetLastId(query);
        author.setId(lastGeneratedId);
    }

    public void deleteAuthor(long id) {
        String queryTemplate = sqlQueryReader.getValue("/author_queries/deleteAuthor").toString();
        String query = String.format(queryTemplate, id);
        executeScalar(query);
    }

    public void updateAuthor(AuthorDTO author) {
        String queryTemplate = sqlQueryReader.getValue("/author_queries/updateAuthor").toString();
        String query = String.format(queryTemplate, author.getName(), author.getLogin(), author.getEmail(), author.getId());
        executeScalar(query);
    }

    public boolean isRecordExistsInDb(long id) {
        List<AuthorDTO> authors = getAllAuthors();
        for (AuthorDTO author : authors
        ) {
            if (author.getId().equals(id))
                return true;
        }
        return false;
    }

}