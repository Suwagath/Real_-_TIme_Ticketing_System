package main.controllers;

import main.dao.impl.SalesLogDAOImpl;
import main.models.SalesLog;


public class SalesLogController {
    // Sales Log
    public void salesLog() {
        System.out.println("\n===== Configure System Parameters =====");
        System.out.println("+-------------------------+-----------------------------------------------------------------+");
        System.out.println("| Log Time and Date       | Log                                                             |");
        System.out.println("+-------------------------+-----------------------------------------------------------------+");
        for (SalesLog salesLog : new SalesLogDAOImpl().getAllLogs()) {
            System.out.printf(
                    "| %-23s | %-63s |%n",
                    salesLog.getTimeAndDate(),
                    salesLog.getLog()
            );
        }
        System.out.println("+-------------------------+-----------------------------------------------------------------+");
    }
}
