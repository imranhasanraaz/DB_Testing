package Tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_access_object.AuthorDAO;
import data_access_object.ProjectDAO;
import data_access_object.SessionDAO;
import data_access_object.TestDAO;
import data_transfer_object.AuthorDTO;
import data_transfer_object.ProjectDTO;
import data_transfer_object.SessionDTO;
import data_transfer_object.TestDTO;
import org.testng.annotations.BeforeClass;
import test_utils.InitiateAuthor;
import test_utils.InitiateProject;

import java.time.LocalDateTime;

public class BaseTest {

    public Logger logger = AqualityServices.getLogger();
    protected SessionDTO session = null;
    protected TestDTO test = null;
    protected AuthorDAO authorDAO = new AuthorDAO();
    protected ProjectDAO projectDAO = new ProjectDAO();
    protected LocalDateTime start_time = LocalDateTime.now();
    protected TestDAO testDAO = new TestDAO();
    protected SessionDAO sessionDAO = new SessionDAO();
    protected ISettingsFile testData = new JsonSettingsFile("Test_Data.json");
    protected int status_id;
    protected Long id = null;
    protected AuthorDTO author = null;
    protected ProjectDTO project = null;

    @BeforeClass
    protected void beforeTest() {
        InitiateProject initiateProject = new InitiateProject();
        InitiateAuthor initiateAuthor = new InitiateAuthor();
        author = initiateAuthor.initiateAuthor();
        project = initiateProject.initiateProject();
    }

}