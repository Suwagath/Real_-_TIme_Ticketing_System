package main.models;

import main.dao.impl.SystemConfigDAOImpl;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;


public class TicketPool {
    private static int totalTickets;
    private static int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();
    private static volatile TicketPool ticketPool;

    // System config data access object
    private static final SystemConfigDAOImpl configDAO = new SystemConfigDAOImpl();

    // Private constructor for Singleton pattern
    private TicketPool() {}

    // Singleton instance with double-checked locking
    public static TicketPool getInstance() {
        if (ticketPool == null) {
            synchronized (TicketPool.class) {
                if (ticketPool == null) {
                    ticketPool = new TicketPool();
                    try {
                        ticketPool.setMaxCapacity(configDAO.findConfigValue("max_ticket_capacity"));
                        ticketPool.setTotalTickets(configDAO.findConfigValue("total_tickets"));

                    } catch (SQLException e) {
                        throw new RuntimeException("Failed to initialize ticket pool from database.", e);
                    }
                }
            }
        }
        return ticketPool;
    }

    // Synchronized method to add tickets
    public boolean addTickets(int count) {
        lock.lock();
        try {
            if (totalTickets + count <= maxCapacity) {
                configDAO.updateConfigValue("total_tickets", totalTickets + count);
                totalTickets += count;
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating total tickets in database.", e);

        } finally {
            lock.unlock();
        }
        return false;
    }

    // Synchronized method to remove a ticket
    public boolean removeTicket() {
        lock.lock();
        try {
            if (totalTickets > 0) {
                configDAO.updateConfigValue("total_tickets", totalTickets -= 1);
                totalTickets -= 1;
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating ticket count in database.", e);

        } finally {
            lock.unlock();
        }
        return false;
    }

    public void reloadSetMaxCapacity() {
        try {
            ticketPool.setMaxCapacity(configDAO.findConfigValue("max_ticket_capacity"));

        } catch (SQLException e) {
            throw new RuntimeException("Failed to reload max capacity in ticket pool.", e);
        }
    }

    public void reloadSetTotalTickets() {
        try {
            ticketPool.setTotalTickets(configDAO.findConfigValue("total_tickets"));

        } catch (SQLException e) {
            throw new RuntimeException("Failed to reload total ticket in ticket pool.", e);
        }
    }

    private void setMaxCapacity(int maxCapacity) {
        lock.lock();
        try {
            TicketPool.maxCapacity = maxCapacity;
        } finally {
            lock.unlock();
        }
    }

    private void setTotalTickets(int totalTickets) {
        lock.lock();
        try {
            TicketPool.totalTickets = totalTickets;
        } finally {
            lock.unlock();
        }
    }
}
