/**
 * Author: James Sharpe and Yosuke Saito
 * Date: April 2019
 * Objective: PackageProductSupplierDB Class for Database connection with SQL to retrieve data
 */




package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageProductSupplierDB
{
    public static ArrayList<PackageProductSupplier> GetPackage(int ID)
    {
        ArrayList<PackageProductSupplier> list = new ArrayList<>();
        PackageProductSupplier ProSupp;

        Connection dbConnect = TravelExpertsDB.getConnection();
        PreparedStatement ps;
        try
        {
            ps = dbConnect.prepareStatement("select PackageId, ProductSupplierId from Packages_Products_Suppliers where ProductSupplierId = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                ProSupp = new PackageProductSupplier();
                ProSupp.setPackageId(rs.getInt(1));
                ProSupp.setProductSupplierId(rs.getInt(2));
                list.add(ProSupp);
            }

            dbConnect.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
            return list;
    }

    public static ArrayList<PackageProductSupplier> GetProSupp(int ID)
    {
        ArrayList<PackageProductSupplier> list = new ArrayList<>();
        PackageProductSupplier ProSupp;

        Connection dbConnect = TravelExpertsDB.getConnection();
        PreparedStatement ps;
        try
        {
            ps = dbConnect.prepareStatement("select PackageId, ProductSupplierId from Packages_Products_Suppliers where PackageId = ?");
            ps.setInt(1, ID);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                ProSupp = new PackageProductSupplier();
                ProSupp.setPackageId(rs.getInt(1));
                ProSupp.setProductSupplierId(rs.getInt(2));
                list.add(ProSupp);
            }

            dbConnect.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean LinkPackageProdSupp(int packageID, int prodSuppID)
    {
        boolean result = false;
        Connection dbConnect = TravelExpertsDB.getConnection();
        try
        {
            PreparedStatement ps = dbConnect.prepareStatement("Insert into Packages_Products_Suppliers(PackageId, ProductSupplierId) Values (?, ?)");
            ps.setInt(1,packageID);
            ps.setInt(2,prodSuppID);

            ps.execute();
            dbConnect.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<PackageProductSupplier> getPackageProSup ( int pkgId){
        ArrayList<PackageProductSupplier> list = new ArrayList<>();
        Connection dbConnect = TravelExpertsDB.getConnection();

        PreparedStatement ps;
        try
        {
            ps = dbConnect.prepareStatement("select Products_Suppliers.ProductSupplierId, ProdName, SupName" +
                    "    from Packages_Products_Suppliers" +
                    "    join Products_Suppliers on Packages_Products_Suppliers.ProductSupplierId = Products_Suppliers.ProductSupplierId" +
                    "    join Suppliers on Suppliers.SupplierId = Products_Suppliers.SupplierId" +
                    "    join Products on Products.ProductId = Products_Suppliers.ProductId" +
                    "    where packageid = ?");
            ps.setInt(1, pkgId);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                list.add(new PackageProductSupplier(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            dbConnect.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteProSupp (int pkg, int proSupp){
        Connection dbConnect = TravelExpertsDB.getConnection();
        try {
            PreparedStatement ps = dbConnect.prepareStatement("DELETE FROM Packages_Products_Suppliers WHERE PackageId = ? AND ProductSupplierId = ?");
            ps.setInt(1, pkg);
            ps.setInt(2, proSupp);

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PackageProductSupplier> getFullPackageProSup (){
        ArrayList<PackageProductSupplier> list = new ArrayList<>();
        Connection dbConnect = TravelExpertsDB.getConnection();

        PreparedStatement ps;
        try
        {
            ps = dbConnect.prepareStatement("select Products_Suppliers.ProductSupplierId, ProdName, SupName" +
                    "    from Packages_Products_Suppliers" +
                    "    join Products_Suppliers on Packages_Products_Suppliers.ProductSupplierId = Products_Suppliers.ProductSupplierId" +
                    "    join Suppliers on Suppliers.SupplierId = Products_Suppliers.SupplierId" +
                    "    join Products on Products.ProductId = Products_Suppliers.ProductId");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                list.add(new PackageProductSupplier(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            dbConnect.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

}
