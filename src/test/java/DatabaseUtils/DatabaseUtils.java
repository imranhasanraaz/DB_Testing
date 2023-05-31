package DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static connection.DbConnection.getConnection;

public class DatabaseUtils {
    private static final Connection connection = getConnection();

    public static Long executeAndGetLastId(String query) {
        try (Statement statement = connection.createStatement()) {
            int rowsAffected = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            int minimumRowAffected = 0;
            int columnIndex = 1;
            if (rowsAffected > minimumRowAffected) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int lastGeneratedId = generatedKeys.getInt(columnIndex);
                    return (long) lastGeneratedId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeAndGetResultSet(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void executeScalar(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}