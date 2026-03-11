package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    /**
     * Initializes the database by executing the SQL file.
     *
     * @param connection   The database connection object.
     * @param sqlFilePath  The path to the SQL file containing the database schema and data.
     */
    public static void initializeDatabase(Connection connection, String sqlFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))) {
            StringBuilder sql = new StringBuilder();
            String line;

            // Read the SQL file line by line
            while ((line = br.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Split the SQL script into individual queries (separated by ";")
            String[] queries = sql.toString().split(";");
            Statement statement = connection.createStatement();

            // Execute each query
            for (String query : queries) {
                if (!query.trim().isEmpty()) { // Skip empty lines
                    statement.execute(query.trim());
                }
            }

            System.out.println("Database initialized successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize the database.");
        }
    }
}