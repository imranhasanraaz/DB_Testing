package test_utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_access_object.ProjectDAO;
import data_transfer_object.ProjectDTO;

public class InitiateProject {
    protected ProjectDAO projectDAO = new ProjectDAO();
    protected ProjectDTO project = null;
    protected ISettingsFile testData = new JsonSettingsFile("Test_Data.json");
    protected long id;

    public ProjectDTO initiateProject() {
        project = new ProjectDTO(
                id,
                testData.getValue("/project_details/project_name").toString()
        );

        projectDAO.addProject(project);
        return project;

    }
}