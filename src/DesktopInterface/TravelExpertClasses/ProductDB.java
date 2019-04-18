package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB {

    private static ArrayList<Product> productList = new ArrayList<>();

    public static ArrayList<Product> GetProducts(){
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

    private static ArrayList<Product> productSpecList = new ArrayList<>();
    public static ArrayList<Product> GetSpecProducts(Integer id){
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

    public static void AddProduct(Product p){
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("INSERT INTO [Products] ([ProdName]) " +
                    " VALUES (?)");
            ps.setString (1, p.getProdName());

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void UpdatePackage (Product oldProduct, Product newProduct){
        Connection dbConnect = TravelExpertsDB.getConnection();

        PreparedStatement ps = null;
        try {
            ps = dbConnect.prepareStatement("UPDATE Products SET ProdName = ? WHERE ProductId = ?");
            ps.setString(1,newProduct.getProdName());
            ps.setInt(2,oldProduct.getProductId());

            ps.executeUpdate();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteProduct (int prod){
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("DELETE FROM [Products] WHERE ProductId = ? ");
            ps.setInt(1, prod);

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
