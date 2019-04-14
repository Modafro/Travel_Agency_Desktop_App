package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.util.ArrayList;

public class SupplierDB {

    public static ArrayList<Supplier> supplierList = new ArrayList<>();

    public static ArrayList<Supplier> GetSuppliers(){
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

    public static void AddSupplier(Supplier sup){
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("INSERT INTO [Suppliers] ([SupName]) " +
                    " VALUES (?)");
            ps.setString (1, sup.getSupName());

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdateSupplier (Supplier oldSupp, Supplier newSupp){
        Connection dbConnect = TravelExpertsDB.getConnection();

        PreparedStatement ps = null;
        try {
            ps = dbConnect.prepareStatement("UPDATE Suppliers SET SupName = ? WHERE SupplierId = ?");
            ps.setString(1,newSupp.getSupName());
            ps.setInt(2,oldSupp.getSupplierId());

            ps.executeUpdate();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteSupplier (int supp){
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("DELETE FROM [Suppliers] WHERE SupplierId = ? ");
            ps.setInt(1, supp);

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
