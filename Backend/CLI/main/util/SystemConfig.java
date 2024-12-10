package main.util;

import main.Main;
import main.controllers.TicketManagementController;
import main.dao.impl.SystemConfigDAOImpl;
import main.dao.impl.VendorDAOImpl;
import java.sql.SQLException;


public class SystemConfig implements Runnable{

    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int vendorCount;

    // System config data access object
    private final static SystemConfigDAOImpl configDAO = new SystemConfigDAOImpl();

    // Manage Tickets
    private final TicketManagementController ticketManagementController;

    private SystemConfig() {
        ticketManagementController = new TicketManagementController();
    }

    public SystemConfig(Main main) {
        ticketManagementController = main.getTicketManagementController();
    }

    @Override
    public void run() {
        try {
            if (configDAO.findConfigValue("system_status") == 1) {
                int tempTicketReleaseRate = configDAO.findConfigValue("ticket_release_rate");
                int tempCustomerRetrievalRate = configDAO.findConfigValue("customer_retrieval_rate");
                int tempVendorCount = new VendorDAOImpl().getVendorCount();

                if (vendorCount != tempVendorCount) {
                    ticketManagementController.restartSystemForVendors();
                }

                if (ticketReleaseRate != tempTicketReleaseRate
                        && ticketReleaseRate > 0) {
                    ticketManagementController.restartSystemForVendors();
                }

                if (ticketReleaseRate != tempCustomerRetrievalRate
                        && customerRetrievalRate > 0) {
                    ticketManagementController.restartSystemForCustomers();
                }

                ticketReleaseRate = tempTicketReleaseRate;
                customerRetrievalRate = tempCustomerRetrievalRate;
                vendorCount = tempVendorCount;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
