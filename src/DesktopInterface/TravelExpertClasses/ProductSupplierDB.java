package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.util.ArrayList;

public class ProductSupplierDB {

    public static ArrayList<ProductSupplier> proSuppList = new ArrayList<>();

    public static ArrayList<ProductSupplier> GetProductSuppliers()
    {
        Connection dbConnect = TravelExpertsDB.getConnection();

        String sql = "Select SP.ProductSupplierId, SP.ProductId, P.ProdName, S.SupName, SP.SupplierId from Products P Join Products_Suppliers SP on P.ProductId = SP.ProductId Join Suppliers S on SP.SupplierId = S.SupplierId";

        try {
            Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                ProductSupplier ps = new ProductSupplier(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                proSuppList.add(ps);
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proSuppList;
    }

    public static Boolean LinkProductSuppliers(int SuppID, int ProdID)
    {
        boolean results = false;
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("Insert into Products_Suppliers(ProductId, SupplierID) VALUES (?, ?)");
            ps.setInt (1, ProdID);
            ps.setInt (2, SuppID);

            ps.execute();
            results = true;
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static ArrayList<ProductSupplier> GetProSupList(int ID, String type)
    {
       ArrayList<ProductSupplier> proSuppList = new ArrayList<>();
       ProductSupplier ProSupp;

       Connection dbConnect = TravelExpertsDB.getConnection();
       PreparedStatement ps;

        try {
            if (type == "Product") {
                ps = dbConnect.prepareStatement("Select SP.ProductSupplierId, SP.ProductId, P.ProdName, S.SupName, SP.SupplierId from Products P Join Products_Suppliers SP on P.ProductId = SP.ProductId Join Suppliers S on SP.SupplierId = S.SupplierId where SP.ProductId = ?");
                ps.setInt(1, ID);
            }
            else
            {
                ps = dbConnect.prepareStatement("Select SP.ProductSupplierId, SP.ProductId, P.ProdName, S.SupName, SP.SupplierId from Suppliers S Join Products_Suppliers SP on S.SupplierId = SP.SupplierId Join Products P on SP.ProductId = P.ProductId where SP.SupplierId = ?");
                ps.setInt(1, ID);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                ProSupp = new ProductSupplier();
                ProSupp.setProductName(rs.getString(3));
                ProSupp.setSupplierName(rs.getString(4));
                ProSupp.setProductId(rs.getInt(2));
                ProSupp.setSupplierId(rs.getInt(5));
                proSuppList.add(ProSupp);
            }

            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proSuppList;
    }
}