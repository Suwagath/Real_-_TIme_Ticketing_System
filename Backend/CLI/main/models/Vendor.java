package main.models;

import main.dao.impl.SalesLogDAOImpl;
import main.dao.impl.SystemConfigDAOImpl;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Vendor implements Runnable {
    private int id;
    private String vendorName;
    private int ticketsPerRelease;
    private int releaseRateSec;
    private TicketPool ticketPool;
    private final SystemConfigDAOImpl configDAO = new SystemConfigDAOImpl();


    @Override
    public void run() {
        try {
            if (configDAO.findConfigValue("system_status") == 1) {
                checkVendorDetails();

                ticketPool.reloadSetMaxCapacity();
                ticketPool.reloadSetTotalTickets();

                if (TicketPool.getInstance().addTickets(ticketsPerRelease)) {
                    new SalesLogDAOImpl().addLog("Add " + ticketsPerRelease +
                            " tickets into ticket pool [ID - " + id + "] Vendor " + vendorName);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to start vendor's periodic ticket release
    public void start(ScheduledExecutorService executorService, TicketPool ticketPool) throws SQLException {
        checkVendorDetails();
        this.ticketPool = ticketPool;
        executorService.scheduleAtFixedRate(this, 0, releaseRateSec, TimeUnit.SECONDS);
    }

    private void checkVendorDetails() throws SQLException {
        if (id == 0 || vendorName == null || ticketsPerRelease == 0 || releaseRateSec == 0) {
            throw new IllegalStateException("Before start or run vendor, setup vendor details");
        }
        setReleaseRateSec(Math.max(configDAO.findConfigValue("ticket_release_rate"), releaseRateSec));
    }

    // Nun argument constructor
    public Vendor() {
    }

    // Full argument constructor
    public Vendor(int id, String vendorName, int ticketsPerRelease, int releaseRateSec) {
        this.id = id;
        this.vendorName = vendorName;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseRateSec = releaseRateSec;
    }

    // Custom constructor (without ID)
    public Vendor(String vendorName, int ticketsPerRelease, int releaseRateSec) {
        this.vendorName = vendorName;
        this.ticketsPerRelease = ticketsPerRelease;
        this.releaseRateSec = releaseRateSec;
    }

    // Get id
    public int getId() {
        return id;
    }

    // Set id
    public void setId(int id) {
        this.id = id;
    }

    // Get vendor name
    public String getVendorName() {
        return vendorName;
    }

    // Set vendor name
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    // Get tickets per release
    public int getTicketsPerRelease() {
        return ticketsPerRelease;
    }

    // Set tickets per release
    public void setTicketsPerRelease(int ticketsPerRelease) {
        this.ticketsPerRelease = ticketsPerRelease;
    }

    // Get release rate sec
    public int getReleaseRateSec() {
        return releaseRateSec;
    }

    // Set release rate sec
    public void setReleaseRateSec(int releaseRateSec) {
        this.releaseRateSec = releaseRateSec;
    }
}
