package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {

    private static SQLiteConnection instance;
    private Connection connection;

    private SQLiteConnection() {
        connect();
    }

    private void connect() {
        String url = "jdbc:sqlite:../realtime_event_ticketing_system_db.db";

        try {
            this.connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static SQLiteConnection getInstance() {
        if (instance == null) {
            instance = new SQLiteConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect(); // Reconnect if the connection is closed
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
