/**
 * Author: James Sharpe
 * Date: April 2019
 * Objective: ProductDB Class for Database connection with SQL to retrieve data
 */



package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB {

    //private static ArrayList<Product> productList = new ArrayList<>();

    public static ArrayList<Product> GetProducts(){

        ArrayList<Product> productList = new ArrayList<>();

        Connection dbConnect = TravelExpertsDB.getConnection();

        String sql = "SELECT ProductId, ProdName FROM Products ORDER BY ProductId";

        try {
            Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                Product product = new Product(rs.getInt(1), rs.getString(2));
                productList.add(product);
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public static ArrayList<Product> GetSpecProducts(Integer id){
        ArrayList<Product> productSpecList = new ArrayList<>();
        Connection dbConnect = TravelExpertsDB.getConnection();
        PreparedStatement ps;
        try {

            ps = dbConnect.prepareStatement("SELECT ProductId, ProdName FROM Products WHERE ProductId != ? ORDER BY ProductId");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Product product = new Product(rs.getInt(1), rs.getString(2));
                productSpecList.add(product);
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productSpecList;
    }

    public static boolean AddProduct(String p){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        try {
            PreparedStatement ps = dbConnect.prepareStatement("INSERT INTO [Products] ([ProdName]) " +
                    " VALUES (?)");
            ps.setString (1, p);

            ps.execute();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean UpdatePackage (int oldProduct, String newProduct){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        PreparedStatement ps = null;
        try {
            ps = dbConnect.prepareStatement("UPDATE Products SET ProdName = ? WHERE ProductId = ?");
            ps.setString(1,newProduct);
            ps.setInt(2,oldProduct);

            ps.executeUpdate();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static boolean DeleteProduct (int prod){
        Connection dbConnect = TravelExpertsDB.getConnection();
        boolean success = false;
        try {
            PreparedStatement ps = dbConnect.prepareStatement("DELETE FROM [Products] WHERE ProductId = ? ");
            ps.setInt(1, prod);

            ps.execute();
            dbConnect.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;

    }
}
