package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final DatabaseConnection INSTANCE = new DatabaseConnection();

    // поменяй под себя
    private static final String URL = "jdbc:postgresql://localhost:5432/Book";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    private DatabaseConnection() { }

    public static DatabaseConnection getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
