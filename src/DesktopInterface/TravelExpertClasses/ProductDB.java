package DesktopInterface.TravelExpertClasses;

import java.sql.*;
import java.util.ArrayList;

public class ProductDB {

    public static ArrayList<Product> productList = new ArrayList<>();

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


}
