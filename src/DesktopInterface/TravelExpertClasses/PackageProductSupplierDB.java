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
            ps.setInt(1,prodSuppID);

            ps.execute();
            dbConnect.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
