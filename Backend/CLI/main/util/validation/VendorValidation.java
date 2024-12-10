package main.util.validation;

import main.dao.impl.VendorDAOImpl;

public class VendorValidation {
    // Method to validate vendor name
    public boolean validateVendorName(String vendorName) {
        if (vendorName == null || vendorName.trim().isEmpty()) {
            System.out.println("Vendor name cannot be empty or null.");
            return false;
        }
        if (vendorName.length() > 45) {
            System.out.println("Vendor name cannot exceed 45 characters.");
            return false;
        }
        if (Character.isDigit(vendorName.charAt(0))) {
            System.out.println("Vendor name cannot start with a number.");
            return false;
        }
        return true;
    }

    // Method to validate the number of tickets per release
    public boolean validateVendorTicketsPerRelease(int vendorTicketsPerRelease) {
        if (vendorTicketsPerRelease <= 0) {
            System.out.println("Number of tickets per release must be greater than 0.");
            return false;
        }
        if (vendorTicketsPerRelease > 10) { // Example max threshold
            System.out.println("Number of tickets per release cannot exceed 10.");
            return false;
        }
        return true;
    }

    // Method to validate the release rate in seconds
    public boolean validateVendorReleaseRateSec(int vendorReleaseRateSec) {
        if (vendorReleaseRateSec <= 0) {
            System.out.println("Release rate must be greater than 0 seconds.");
            return false;
        }
        if (vendorReleaseRateSec > 300) { // Example max threshold: 5 minutes
            System.out.println("Release rate cannot exceed 3600 seconds (5 minutes).");
            return false;
        }
        return true;
    }

    // Method to validate vendor Id is avalanche in DB
    public boolean existsVendor(int vendorId) {
        return new VendorDAOImpl().existsVendor(vendorId);
    }

}
