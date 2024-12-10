package main.dao.impl;

import main.dao.VendorDAO;
import main.db.SQLiteConnection;
import main.models.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VendorDAOImpl implements VendorDAO {
    private static Connection connection = SQLiteConnection.getInstance().getConnection();

    public VendorDAOImpl() {
        try {
            Statement stmt = connection.createStatement();

            // Create the table if it doesn't exist
            stmt.execute("CREATE TABLE IF NOT EXISTS vendors (\n" +
                    "    vendor_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    vendor_name TEXT UNIQUE NOT NULL,\n" +
                    "    tickets_per_release INTEGER NOT NULL, \n" +
                    "    release_rate_sec INTEGER NOT NULL  \n" +
                    ");");

            if (getVendorCount() < 3) {
                addVendor(new Vendor("vendor - 1", 1, 30));
                addVendor(new Vendor("vendor - 2", 2, 60));
                addVendor(new Vendor("vendor - 3", 3, 120));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addVendor(Vendor vendor) {
        String query = "INSERT OR IGNORE INTO vendors(vendor_name, tickets_per_release, release_rate_sec) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, vendor.getVendorName());
            preparedStatement.setInt(2, vendor.getTicketsPerRelease());
            preparedStatement.setInt(3, vendor.getReleaseRateSec());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateVendor(Vendor vendor) {
        String query = "UPDATE vendors SET vendor_name = ?, tickets_per_release = ?, release_rate_sec = ? WHERE vendor_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, vendor.getVendorName());
            preparedStatement.setInt(2, vendor.getTicketsPerRelease());
            preparedStatement.setInt(3, vendor.getReleaseRateSec());
            preparedStatement.setInt(4, vendor.getId());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteVendor(int vendorId) {
        String query = "DELETE FROM vendors WHERE vendor_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, vendorId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Vendor getVendor(int vendorId) {
        Vendor vendor = new Vendor();
        String query = "SELECT * FROM vendors WHERE vendor_id = ?";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                vendor.setId(resultSet.getInt("vendor_id"));
                vendor.setVendorName(resultSet.getString("vendor_name"));
                vendor.setTicketsPerRelease(resultSet.getInt("tickets_per_release"));
                vendor.setReleaseRateSec(resultSet.getInt("release_rate_sec"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return vendor;
    }

    @Override
    public boolean existsVendor(int vendorId) {
        String query = "SELECT COUNT(*) FROM vendors WHERE vendor_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vendorId); // Set the vendorId in the query

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // Retrieve the COUNT(*) result
                return count > 0; // If count > 0, vendor exists
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false; // Return false if vendor does not exist or an error occurs
    }

    @Override
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT * FROM vendors;";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                vendors.add(new Vendor(
                        resultSet.getInt("vendor_id"),
                        resultSet.getString("vendor_name"),
                        resultSet.getInt("tickets_per_release"),
                        resultSet.getInt("release_rate_sec")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vendors;
    }

    @Override
    public int getVendorCount() {
        String query = "SELECT COUNT(*) FROM vendors;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1); // Get the count from the first column
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
