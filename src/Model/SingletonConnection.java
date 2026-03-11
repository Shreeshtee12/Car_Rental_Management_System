package Model;

import Util.DatabaseInitializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SingletonConnection {
	// Database connection URL
	private final String url = "jdbc:mysql://localhost:3306/carrentaloffice?useSSL=false&allowPublicKeyRetrieval=true";
	// Username
	private final String user = "hemanu";
	// Password
	private final String pass = "manu143";
	// Connection object
	private static Connection connection;

	// Path to the SQL file
	private final String sqlFilePath = "src/sql/DB.sql"; // Update this path as per your project structure

	// Private constructor
	private SingletonConnection() {
		try {
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected to the database successfully!");

			// Check if database initialization is required
			if (!isDatabaseInitialized()) {
				System.out.println("Database not initialized. Running SQL script...");
				DatabaseInitializer.initializeDatabase(connection, sqlFilePath);
			} else {
				System.out.println("Database is already initialized.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to connect to the database!");
		}
	}

	// This method returns the connection instance if already created, or create an instance
	public static Connection getInstance() {
		if (connection == null)
			new SingletonConnection();
		return connection;
	}

	// Method to check if the database is initialized
	private boolean isDatabaseInitialized() {
		try (Statement stmt = connection.createStatement()) {
			// Check if the 'car' table exists
			ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'car';");
			return rs.next(); // If result set has data, the table exists
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Cleanup resources
	public void finalize() throws Throwable {
		if (connection != null && !connection.isClosed()) {
			connection.close();
			System.out.println("Database connection closed.");
		}
	}
}