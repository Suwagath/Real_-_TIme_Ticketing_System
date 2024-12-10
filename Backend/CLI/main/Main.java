package main;

import main.controllers.ConfigureSystemParametersController;
import main.controllers.SalesLogController;
import main.controllers.TicketManagementController;
import main.controllers.VendorManagementController;
import main.dao.impl.SystemConfigDAOImpl;
import main.db.SQLiteConnection;
import main.util.SystemConfig;
import main.util.UserInputGetCollection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main {

    // To get inputs
    private final UserInputGetCollection userInputGetCollection = new UserInputGetCollection();

    // Database connection
    private final Connection connection = SQLiteConnection.getInstance().getConnection();

    // Initialize ScheduledExecutorService
    private final ScheduledExecutorService executorServiceSystemConfig = Executors.newScheduledThreadPool(1);

    private final SystemConfigDAOImpl systemConfigDAO = new SystemConfigDAOImpl();

    // 1. Configure System Parameters
    private final ConfigureSystemParametersController configureSystemParametersController = new ConfigureSystemParametersController();

    // 2. Manage Vendors
    private final VendorManagementController vendorManagementController = new VendorManagementController();

    // 3. Manage Tickets
    private final TicketManagementController ticketManagementController = new TicketManagementController();

    // 4. Sales Log
    private final SalesLogController salesLogController = new SalesLogController();

    // main.Main menu
    private int mainMenu() {
        System.out.println("""
        ===== Real-Time Event Ticketing System =====
        1. Configure System Parameters
        2. Vendors Management
        3. Ticket Management
        4. View Sales Log
        0. Exit
        ============================================
        """);
        return userInputGetCollection.getUserInputInt("Please select an option (0-4):> ");
    }

    public static void main(String[] args) {
        Main main = new Main();
        boolean exit;
        try {
            boolean dbConnection = main.connection != null && !main.connection.isClosed();
            System.out.println(dbConnection ? "Database connected successfully." : "Database connection failed.");
            exit = dbConnection;
            main.systemConfigDAO.updateConfigValue("cli_status", 1);

        } catch (SQLException e) {
            exit = false;
            System.out.println(e.getMessage());
        }

        main.executorServiceSystemConfig.scheduleAtFixedRate(
                new SystemConfig(main), 0, 5, TimeUnit.SECONDS);

        while (exit) {
            System.out.println();
            switch (main.mainMenu()) {
                case -1:    // For invalid input skip
                    break;

                case 0:     // 0. Exit
                    exit = !main.userInputGetCollection.getUserInputString(
                            "Are you sure you want to exit? (y/n):> ")
                            .equalsIgnoreCase("y");
                    try {
                        if (!exit) {
                            main.systemConfigDAO.updateConfigValue("cli_status", 0);
                            main.ticketManagementController.stopSystem();

                            // Add a shutdown hook to gracefully stop the executor service
                            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                                main.executorServiceSystemConfig.shutdown();
                                try {
                                    if (!main.executorServiceSystemConfig.awaitTermination(5, TimeUnit.SECONDS)) {
                                        main.executorServiceSystemConfig.shutdownNow();
                                    }

                                } catch (InterruptedException e) {
                                    main.executorServiceSystemConfig.shutdownNow();
                                }
                            }));
                            main.connection.close();
                            System.exit(0);
                        }

                    } catch (SQLException e) {
                        System.out.println("DB connection failed.\n" + e.getMessage());
                    }
                    break;

                case 1:     // 1. Configure System Parameters
                    main.configureSystemParametersController.configureSystemParameters();
                    break;

                case 2:     // 2. Manage Vendors
                    main.vendorManagementController.vendorsManagement();
                    break;

                case 3:     // 3. Manage Tickets
                    main.ticketManagementController.ticketManagement();
                    break;

                case 4:     // 4. Sales Log
                    main.salesLogController.salesLog();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    public TicketManagementController getTicketManagementController() {
        return ticketManagementController;
    }

}
