package main.controllers;

import main.dao.VendorDAO;
import main.dao.impl.VendorDAOImpl;
import main.models.Vendor;
import main.util.UserInputGetCollection;
import main.util.validation.VendorValidation;

import java.util.List;


public class VendorManagementController {
    // To get inputs
    private final UserInputGetCollection uic = new UserInputGetCollection();

    // System config data access object
    private final static VendorDAO configDAO = new VendorDAOImpl();

    // Vendor validation
    private final static VendorValidation validation = new VendorValidation();

    // Manage vendors menu
    private int manageVendorsMenu() {
        System.out.println("""
        ===== Manage Vendors =====
        1. Show All Vendors
        2. Add Vendor
        3. Update Vendor
        4. Remove Vendor
        5. Back to Main Menu
        ============================================
        """);
        return uic.getUserInputInt("Please select an option (1-5):> ");
    }

    // Manage Vendors
    public void vendorsManagement() {
        boolean exit = true;

        while (exit) {
            System.out.println();
            switch (manageVendorsMenu()) {
                case -1:    // For invalid input skip
                    break;

                case 1:     // 1. Show All Vendors
                    showAllVendors();
                    break;

                case 2:     // 2. Add Vendor
                    addVendor();
                    break;

                case 3:     // 3. Update Vendor
                    updateVendor();
                    break;

                case 4:     // 4. Remove Vendor
                    removeVendor();
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

    // 1. Add Vendor
    private void addVendor() {
        System.out.println("\n===== Add Vendor =====");
        System.out.println(configDAO.addVendor(new Vendor(getVendorName(),
                getTicketsPerRelease(),
                getReleaseRateSec())) ? "Successfully added vendor.\n" : "Failed to added vendor.");
    }

    // 2. Remove Vendor
    private void removeVendor() {
        System.out.println("\n===== Remove Vendor =====");
        System.out.println(configDAO.deleteVendor(getVendorId())
                ? "Successfully deleted vendor.\n"
                : "Failed to delete vendor.");
    }

    // 3. Show All Vendors
    private void showAllVendors() {
        System.out.println("\n===== Show All Vendors =====");
        System.out.println("+-----+-------------------------+---------------------+--------------------+");
        System.out.println("| ID  | Vendor Name             | Tickets Per Release | Release Rate (sec) |");
        System.out.println("+-----+-------------------------+---------------------+--------------------+");
        // Print each vendor in a formatted row
        for (Vendor vendor : configDAO.getAllVendors()) {
            System.out.printf(
                    "| %-3d | %-23s | %-19d | %-18d |%n",
                    vendor.getId(),
                    vendor.getVendorName(),
                    vendor.getTicketsPerRelease(),
                    vendor.getReleaseRateSec()
            );
        }
        System.out.println("+-----+-------------------------+---------------------+--------------------+");
    }

    // 4. Update Vendor
    private void updateVendor() {
        System.out.println("\n===== Update Vendor =====");
        System.out.println(configDAO.updateVendor(new Vendor(
                getVendorId(),
                getVendorName(),
                getTicketsPerRelease(),
                getReleaseRateSec())) ? "Successfully update vendor.\n": "Failed to update vendor.\n");
    }

    private String getVendorName() {
        while (true) {
            String vendorName = uic.getUserInputString("Please enter a vendor name:> ");
            if (validation.validateVendorName(vendorName)) {
                return vendorName;
            }
        }
    }

    private int getTicketsPerRelease() {
        while (true) {
            int ticketsPerRelease = uic.getUserInputInt("Please enter a tickets per release:> ");
            if (validation.validateVendorTicketsPerRelease(ticketsPerRelease)) {
                return ticketsPerRelease;
            }
        }
    }

    private int getReleaseRateSec() {
        while (true) {
            int releaseRateSec = uic.getUserInputInt("Please enter a release rate:> ");
            if (validation.validateVendorReleaseRateSec(releaseRateSec)) {
                return releaseRateSec;
            }
        }
    }

    private int getVendorId() {
        while (true) {
            int vendorId = uic.getUserInputInt("Please enter a vendor id:> ");
            if (validation.existsVendor(vendorId)) {
                return vendorId;
            }
        }
    }

}
