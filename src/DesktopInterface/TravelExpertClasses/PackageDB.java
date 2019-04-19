package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PackageDB {

    public static ArrayList<Package> packageList = new ArrayList<>();

    public static ArrayList<Package> GetPackages(){
        Connection dbConnect = TravelExpertsDB.getConnection();

        String sql = "SELECT PackageId, PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice, PkgAgencyCommission FROM Packages ORDER BY PackageId";

        try {
            Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                Package travelPackage = new Package(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5), rs.getDouble(6), rs.getDouble(7));
                packageList.add(travelPackage);
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packageList;
    }

    public static void AddPackage(Package p){

        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            PreparedStatement ps = dbConnect.prepareStatement("INSERT INTO [Packages] ([PkgName], [PkgStartDate], [PkgEndDate], [PkgDesc], [PkgBasePrice], [PkgAgencyCommission]) " +
                                                                " VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString (1, p.getPkgName());
            ps.setDate (2, p.getPkgStartDate());
            ps.setDate (3, p.getPkgEndDate());
            ps.setString (4, p.getPkgDesc());
            ps.setDouble (5, p.getPkgBasePrice());
            ps.setDouble(6, p.getPkgAgencyCommission());

            ps.execute();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert_error = new Alert(Alert.AlertType.ERROR);
            alert_error.setTitle("Insert Status");
            alert_error.setHeaderText("Package was not added");
            alert_error.setContentText("An error occurred while trying to add customer to database. Please" +
                    " try again. If issue persists, contact your database administrator.");
            alert_error.showAndWait();
        }
    }

    public static void UpdatePackage (Package oldPackage, Package newPackage){
        Connection dbConnect = TravelExpertsDB.getConnection();

        PreparedStatement ps = null;
        try {
            ps = dbConnect.prepareStatement("UPDATE PACKAGES SET PkgName = ?, PkgDesc = ?, PkgStartDate = ?, PkgEndDate = ?, PkgBasePrice = ?, PkgAgencyCommission = ? WHERE PackageId = ?");
            ps.setString(1,newPackage.getPkgName());
            ps.setString(2,newPackage.getPkgDesc());
            ps.setDate(3,newPackage.getPkgStartDate());
            ps.setDate(4,newPackage.getPkgEndDate());
            ps.setDouble(5,newPackage.getPkgBasePrice());
            ps.setDouble(6,newPackage.getPkgAgencyCommission());
            ps.setInt(7,oldPackage.getPackageId());

            ps.executeUpdate();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean DeletePackage (Package pkg){

        boolean isPkgDeleted = false;
        Connection dbConnect = TravelExpertsDB.getConnection();

        try {
            //first delete package referenced in packages_prodcuts_suppliers if any
            PreparedStatement stmt = dbConnect.prepareStatement("DELETE Packages_Products_Suppliers WHERE PackageId = ? ");
            stmt.setInt(1, pkg.getPackageId());
            stmt.executeUpdate();

            PreparedStatement ps = dbConnect.prepareStatement("DELETE [Packages] WHERE PackageId = ? ");
            ps.setInt(1, pkg.getPackageId());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0)
            {
                isPkgDeleted = true;
            }
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isPkgDeleted;
    }

    public static ObservableList<Package> getPackageNameTableView(ObservableList<Package> pkgData)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select PackageId, PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice from Packages where PkgStartDate >= GetDate()";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                pkgData.add(new Package(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getDouble(6)
                ));
            }
        }
        catch (SQLException e) {
        e.printStackTrace();
        }

        return pkgData;
    }

    //method to search values in database targeted for Packages GUI
    public static ArrayList<Package> PackageSearchResult(ArrayList<Package> pkgData, String userInput)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select * from Packages where lower(CONCAT(PkgName,PkgDesc, PkgBasePrice, PkgAgencyCommission)) like ?";

        try
        {
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,"%"+userInput.toLowerCase()+"%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                pkgData.add(new Package(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getDouble(7)
                ));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return pkgData;
    }

    //method to search values in database (package name only) targeted for the Bookings GUI
    public static ObservableList<Package> PackageNameSearchResult(ObservableList<Package> pkgData, String userInput)
    {
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "select PackageId, PkgName, PkgStartDate, PkgEndDate, PkgDesc, PkgBasePrice from Packages where lower(PkgName) like ? AND PkgStartDate >= GetDate()";

        try
        {
            PreparedStatement stmt = null;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%"+userInput.toLowerCase()+"%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
              pkgData.add(new Package(
                      rs.getInt(1),
                      rs.getString(2),
                      rs.getDate(3),
                      rs.getDate(4),
                      rs.getString(5),
                      rs.getDouble(6)
              ));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return pkgData;
    }
}
