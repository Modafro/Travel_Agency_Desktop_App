package DesktopInterface.TravelExpertClasses;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Package {

    private int packageId;
    private String pkgName;
    private java.sql.Date pkgStartDate;
    private java.sql.Date pkgEndDate;
    private String pkgDesc;
    private double pkgBasePrice;
    private double pkgAgencyCommission;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public java.sql.Date getPkgStartDate() {
        return pkgStartDate;
    }

    public void setPkgStartDate(java.sql.Date pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }

    public java.sql.Date getPkgEndDate() {
        return pkgEndDate;
    }

    public void setPkgEndDate(java.sql.Date pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public double getPkgBasePrice() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice = pkgBasePrice;
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public Package(int packageId, String pkgName, Date pkgStartDate, Date pkgEndDate, String pkgDesc, double pkgBasePrice, double pkgAgencyCommission) {
        this.packageId = packageId;
        this.pkgName = pkgName;
        this.pkgStartDate = pkgStartDate;
        this.pkgEndDate = pkgEndDate;
        this.pkgDesc = pkgDesc;
        this.pkgBasePrice = pkgBasePrice;
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public Package(int packageId, String pkgName, Date pkgStartDate, Date pkgEndDate, String pkgDesc, Double pkgBasePrice){
        this.packageId = packageId;
        this.pkgName = pkgName;
        this.pkgStartDate = pkgStartDate;
        this.pkgEndDate = pkgEndDate;
        this.pkgDesc = pkgDesc;
        this.pkgBasePrice = pkgBasePrice;
    }

    public Package() {
    }

    @Override
    public String toString() {
        return "DesktopInterface.TravelExpertClasses.Package{" +
                "packageId=" + packageId +
                ", pkgName='" + pkgName + '\'' +
                ", pkgStartDate=" + pkgStartDate +
                ", pkgEndDate=" + pkgEndDate +
                ", pkgDesc='" + pkgDesc + '\'' +
                ", pgBasePrice=" + pkgBasePrice +
                ", pkgAgencyCommission=" + pkgAgencyCommission +
                '}';
    }
}
