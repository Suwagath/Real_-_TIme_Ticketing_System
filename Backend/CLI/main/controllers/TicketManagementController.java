package main.controllers;

import main.dao.SystemConfigDAO;
import main.dao.impl.SystemConfigDAOImpl;
import main.dao.impl.VendorDAOImpl;
import main.models.Customer;
import main.models.TicketPool;
import main.models.Vendor;
import main.util.UserInputGetCollection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TicketManagementController {
    // To get inputs
    private final UserInputGetCollection uic = new UserInputGetCollection();

    // System config data access object
    private final SystemConfigDAO configDAO = new SystemConfigDAOImpl();

    // Ticket pool
    private static final TicketPool ticketPool = TicketPool.getInstance();

    // Initialize ScheduledExecutorService
    private final ScheduledExecutorService executorServiceVendor = Executors.newScheduledThreadPool(3);

    // Initialize ScheduledExecutorService
    private final ScheduledExecutorService executorServiceCustomer = Executors.newScheduledThreadPool(1);

    private static final ArrayList<Customer> vipCustomers = new ArrayList<>();
    private static final ArrayList<Customer> customers = new ArrayList<>();

    // Constructor
    public TicketManagementController() {
        try {
            if (configDAO.findConfigValue("system_status") == 1) {
                startSystem();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Ticket parameters menu
    private int configureSystemParametersMenu() {
        System.out.println("""
        ===== Ticket Management =====
        1. Show Status
        2. Start System
        3. Stop System
        4. Restart System
        5. Back to Main Menu
        ============================================
        """);
        return uic.getUserInputInt("Please select an option (1-5):> ");
    }

    // Manage Tickets
    public void ticketManagement() {
        boolean exit = true;

        while (exit) {
            System.out.println();
            switch (configureSystemParametersMenu()) {
                case -1:    // For invalid input skip
                    break;

                case 1:     // 1. Show Status
                    showStatus();
                    break;

                case 2:     // 2. Start System
                    setStartSystem();
                    break;

                case 3:     // 3. Stop System
                    setStopSystem();
                    break;

                case 4:     // 4. Restart System
                    setRestartSystem();
                    break;

                case 5:     // 5. Back to Main Menu
                    exit = false;
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    // Start system
    public void startSystem() throws SQLException {
        startSystemForVendors();
        startSystemForCustomers();
    }

    // Start system for vendors
    public void startSystemForVendors() throws SQLException {
        for (Vendor v : new VendorDAOImpl().getAllVendors()) {
            v.start(executorServiceVendor, ticketPool);
        }
    }

    // Start system for customers
    public void startSystemForCustomers() throws SQLException {
        // Schedule a new customer task at a fixed rate (retrievalRateSec)
        executorServiceCustomer.scheduleAtFixedRate(() -> {
            try {
                Customer customer1 = new Customer(ticketPool);
                Customer customer2 = new Customer(ticketPool);

                if (customer1.isVip()) {
                    vipCustomers.add(customer1);
                } else {
                    customers.add(customer1);
                }

                if (customer2.isVip()) {
                    vipCustomers.add(customer2);
                } else {
                    customers.add(customer2);
                }

                if (!vipCustomers.isEmpty()) {
                    new Thread(vipCustomers.getFirst()).start();
                    vipCustomers.removeFirst();

                } else {
                    new Thread(customers.getFirst()).start();
                    customers.removeFirst();
                }

            } catch (SQLException e) {
                System.err.println("Error creating a new customer: " + e.getMessage());
            }
        }, 0,
                configDAO.findConfigValue("customer_retrieval_rate"),
                TimeUnit.SECONDS);

    }

    // Stop system
    public void stopSystem() {
        try {
            stopSystemForVendors();
            stopSystemForCustomers();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Stop system for vendors
    public void stopSystemForVendors() throws SQLException {
        // Add a shutdown hook to gracefully stop the executor service
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorServiceVendor.shutdown();
            try {
                if (!executorServiceVendor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorServiceVendor.shutdownNow();
                }

            } catch (InterruptedException e) {
                executorServiceVendor.shutdownNow();
            }
        }));
    }

    // Stop system for customers
    public void stopSystemForCustomers() throws SQLException {
        // Add a shutdown hook to gracefully stop the executor service
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorServiceCustomer.shutdown();
            try {
                if (!executorServiceCustomer.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorServiceCustomer.shutdownNow();
                }

            } catch (InterruptedException e) {
                executorServiceCustomer.shutdownNow();
            }
        }));
    }

    // Restart system
    public void restartSystem() throws SQLException {
        restartSystemForVendors();
        restartSystemForCustomers();
    }

    // Restart system for vendors
    public void restartSystemForVendors() throws SQLException {
        stopSystemForVendors();
        startSystemForVendors();
    }

    // Restart system for customers
    public void restartSystemForCustomers() throws SQLException {
        stopSystemForCustomers();
        startSystemForCustomers();
    }

    // 1. Show Status
    private void showStatus() {
        try {
            System.out.println(
                "\n===== Show Status =====\n" +
                (configDAO.findConfigValue("system_status") == 1
                ? "System is up and running\n"
                : "System is not running\n") +
                "============================================"
            );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 2. Start System
    private void setStartSystem() {
        try {
            configDAO.updateConfigValue("system_status", 1);
            startSystem();
            System.out.println("System is up and running.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 3. Stop System
    private void setStopSystem() {
        try {
            configDAO.updateConfigValue("system_status", 0);
            stopSystem();
            System.out.println("System has been stopped.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 4. Restart System
    private void setRestartSystem() {
        try {
            configDAO.updateConfigValue("system_status", 1);
            restartSystem();
            System.out.println("System has been restarted.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
