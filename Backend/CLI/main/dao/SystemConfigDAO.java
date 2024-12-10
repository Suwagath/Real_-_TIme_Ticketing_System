package main.dao;

import java.sql.SQLException;


public interface SystemConfigDAO {
    int findConfigValue(String configKey) throws SQLException;
    void updateConfigValue(String configKey, int configValue) throws SQLException;
}
