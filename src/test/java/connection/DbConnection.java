package connection;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final ISettingsFile configReader = new JsonSettingsFile("Config.json");
    private static final String DB_URL = configReader.getValue("/db_connection/connection_string").toString();
    private static final String USER = configReader.getValue("/db_connection/user").toString();
    private static final String PASSWORD = configReader.getValue("/db_connection/password").toString();
    private static Connection connection;

    private DbConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            new DbConnection();
        }
        return connection;
    }
}