package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingsDB {

    //method to add new booking
    public static boolean addBookingForCustId(Bookings bkg, Customer c, Package pkg)
    {
        boolean isBkgAdded = false;
        Connection conn = TravelExpertsDB.getConnection();

        String sql = "INSERT INTO Bookings (BookingDate, TravelerCount, CustomerId, TripTypeId, PackageId) "+
                "Values (?,?,?,?,?)";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, bkg.getBookingDate());
            stmt.setFloat(2, bkg.getTravelerCount());
            stmt.setInt(3, c.getCustomerId());
            stmt.setString(4,bkg.getTripTypeId());
            stmt.setInt(5,pkg.getPackageId());

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0 )
            {
                isBkgAdded = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return isBkgAdded;
    }
}
