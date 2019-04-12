package DesktopInterface.TravelExpertClasses;

import java.sql.Date;

public class Bookings {
    private int BookingId;
    private Date BookingDate;
    private String BookingNo;
    private float TravelerCount;
    private int CustomerId;
    private String TripTypeId;
    private int PackageId;

    public Bookings(int bookingId, Date bookingDate, String bookingNo, float travelerCount, int customerId, String tripTypeId, int packageId) {
        BookingId = bookingId;
        BookingDate = bookingDate;
        BookingNo = bookingNo;
        TravelerCount = travelerCount;
        CustomerId = customerId;
        TripTypeId = tripTypeId;
        PackageId = packageId;
    }

    public Bookings(Date bookingDate, float travelerCount, int customerId, String tripTypeId, int packageId) {
        BookingDate = bookingDate;
        TravelerCount = travelerCount;
        CustomerId = customerId;
        TripTypeId = tripTypeId;
        PackageId = packageId;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingNo() {
        return BookingNo;
    }

    public void setBookingNo(String bookingNo) {
        BookingNo = bookingNo;
    }

    public float getTravelerCount() {
        return TravelerCount;
    }

    public void setTravelerCount(float travelerCount) {
        TravelerCount = travelerCount;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getTripTypeId() {
        return TripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        TripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return PackageId;
    }

    public void setPackageId(int packageId) {
        PackageId = packageId;
    }
}
