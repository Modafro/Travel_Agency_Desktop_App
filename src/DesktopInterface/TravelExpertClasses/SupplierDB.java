package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.util.ArrayList;

public class SupplierDB {

    //public static ArrayList<Supplier> supplierList = new ArrayList<>();

    public static ArrayList<Supplier> GetSuppliers(){
        ArrayList<Supplier> supplierList = new ArrayList<>();
        Connection dbConnect = TravelExpertsDB.getConnection();

        String sql = "SELECT SupplierId, SupName FROM Suppliers ORDER BY SupplierId";

        try {
            Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                Supplier supplier = new Supplier(rs.getInt(1), rs.getString(2));
                supplierList.add(supplier);
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    public static boolean AddSupplier(String sup){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        try {
            PreparedStatement ps = dbConnect.prepareStatement("INSERT into Suppliers (SupplierID, SupName ) VALUES((select top 1 SupplierId from Suppliers order by 1 desc)+ 1, ?)");
            ps.setString (1, sup);

            ps.execute();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean UpdateSupplier (int oldSuppId, String newSupp){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        PreparedStatement ps = null;
        try {
            ps = dbConnect.prepareStatement("UPDATE Suppliers SET SupName = ? WHERE SupplierId = ?");
            ps.setString(1,newSupp);
            ps.setInt(2,oldSuppId);

            ps.executeUpdate();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean DeleteSupplier (int supp){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        try {
            PreparedStatement ps = dbConnect.prepareStatement("DELETE FROM [Suppliers] WHERE SupplierId = ? ");
            ps.setInt(1, supp);

            ps.execute();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
