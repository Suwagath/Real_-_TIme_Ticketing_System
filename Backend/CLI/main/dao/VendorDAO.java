package main.dao;

import main.models.Vendor;
import java.util.List;


public interface VendorDAO {
    boolean addVendor(Vendor vendor);
    boolean updateVendor(Vendor vendor);
    boolean deleteVendor(int vendorId);
    Vendor getVendor(int vendorId);
    List<Vendor> getAllVendors();
    boolean existsVendor(int vendorId);
    int getVendorCount();
}
