package main.dao;

import main.models.SalesLog;
import java.util.List;


public interface SalesLogDAO {
    void addLog(String log);
    List<SalesLog> getAllLogs();
}
