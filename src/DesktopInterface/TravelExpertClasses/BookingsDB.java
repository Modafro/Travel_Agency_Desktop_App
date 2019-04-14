package DesktopInterface.TravelExpertClasses;

import DesktopInterface.TravelExpertsDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public static ArrayList<Bookings> getBookings ()
    {
        ArrayList<Bookings> bookingList = new ArrayList<>();
        Connection conn = TravelExpertsDB.getConnection();
        String sql = "Select from BookingDate, TravelerCount, CustomerId, TripTypeId, PackageId Bookings";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                Bookings booking = new Bookings(rs.getInt(1), rs.getDate(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6), rs.getInt(7));
                bookingList.add(booking);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingList;
    }
}
