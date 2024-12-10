package main.dao.impl;

import main.dao.SystemConfigDAO;
import main.db.SQLiteConnection;
import java.sql.*;


public class SystemConfigDAOImpl implements SystemConfigDAO {

    public SystemConfigDAOImpl() {
        try {
            Connection connection = SQLiteConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();

            // Create the table if it doesn't exist
            stmt.execute("CREATE TABLE IF NOT EXISTS system_config (\n" +
                    "    id INTEGER PRIMARY KEY,\n" +
                    "    config_key TEXT NOT NULL UNIQUE,\n" +
                    "    config_value INT NOT NULL \n" +
                    ")");

            // Insert default values, ignoring if they already exist
            insertDefaultInputs("total_tickets", 50);
            insertDefaultInputs("ticket_release_rate", 60);
            insertDefaultInputs("customer_retrieval_rate", 60);
            insertDefaultInputs("max_ticket_capacity", 500);
            insertDefaultInputs("system_status", 0);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert default inputs
    private void insertDefaultInputs(String configKey, int configValue) throws SQLException {
        String query = "INSERT OR IGNORE INTO system_config (config_key, config_value) VALUES (?, ?)";

        Connection connection = SQLiteConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, configKey);
        preparedStatement.setInt(2, configValue);
        preparedStatement.executeUpdate();
    }

    @Override
    public int findConfigValue(String configKey) throws SQLException {
        String query = "SELECT config_value FROM system_config WHERE config_key = ?";

        Connection connection = SQLiteConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, configKey);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("config_value");

        } else {
            System.out.println(configKey + " No matching config key found.");
            return -1;
        }
    }

    @Override
    public void updateConfigValue(String configKey, int configValue) throws SQLException {
        String query = "UPDATE system_config SET config_value = ? WHERE config_key = ?";

        Connection connection = SQLiteConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, configValue);
        preparedStatement.setString(2, configKey);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
//                System.out.println("Config value updated successfully.");

        } else {
            System.out.println("No matching config key found.");
        }
    }

}