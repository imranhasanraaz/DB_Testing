package test_utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_access_object.AuthorDAO;
import data_transfer_object.AuthorDTO;

public class InitiateAuthor {
    protected AuthorDTO author = null;
    protected AuthorDAO authorDAO = new AuthorDAO();
    protected ISettingsFile testData = new JsonSettingsFile("Test_Data.json");
    protected long id;


    public AuthorDTO initiateAuthor() {
        author = new AuthorDTO(
                id,
                testData.getValue("/project_details/project_author/name").toString(),
                testData.getValue("/project_details/project_author/login").toString(),
                testData.getValue("/project_details/project_author/email").toString()
        );
        authorDAO.addAuthor(author);
        return author;

    }

}