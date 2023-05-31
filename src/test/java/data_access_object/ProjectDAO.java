package data_access_object;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import data_transfer_object.ProjectDTO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static DatabaseUtils.DatabaseUtils.*;

public class ProjectDAO {
    private final ISettingsFile sqlQueryReader = new JsonSettingsFile("Queries.json");

    public ProjectDTO getProjectById(long id) {
        String queryTemplate = sqlQueryReader.getValue("/project_queries/getProjectById").toString();
        String query = String.format(queryTemplate, id);
        ResultSet resultSet = executeAndGetResultSet(query);
        try {
            if (resultSet != null && resultSet.next()) {
                return new ProjectDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProjectDTO> getAllProjects() {
        String query = sqlQueryReader.getValue("/project_queries/getAllProjects").toString();
        ResultSet resultSet = executeAndGetResultSet(query);
        List<ProjectDTO> projects = new ArrayList<>();
        try {
            while (resultSet != null && resultSet.next()) {
                projects.add(new ProjectDTO(
                        resultSet.getLong("id"),
                        resultSet.getString("name")
                ));
            }
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addProject(ProjectDTO project) {
        String queryTemplate = sqlQueryReader.getValue("/project_queries/addProject").toString();
        String query = String.format(queryTemplate, project.getName());
        Long lastGeneratedId = executeAndGetLastId(query);
        project.setId(lastGeneratedId);
    }

    public void updateProject(ProjectDTO project) {
        String queryTemplate = sqlQueryReader.getValue("/project_queries/updateProject").toString();
        String query = String.format(queryTemplate, project.getName(), project.getId());
        executeScalar(query);
    }

    public void deleteProject(long id) {
        String queryTemplate = sqlQueryReader.getValue("/project_queries/deleteProject").toString();
        String query = String.format(queryTemplate, id);
        executeScalar(query);
    }

    public boolean isRecordExistsInDb(long id) {
        List<ProjectDTO> projects = getAllProjects();
        for (ProjectDTO project : projects
        ) {
            if (project.getId().equals(id))
                return true;
        }
        return false;
    }
}