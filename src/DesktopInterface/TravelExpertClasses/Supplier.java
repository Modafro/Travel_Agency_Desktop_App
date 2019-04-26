/**
 * Author: James Sharpe
 * Date: April 2019
 * Objective: Supplier Class for storing and retrieving supplier data
 */



package DesktopInterface.TravelExpertClasses;

public class Supplier {

    public int supplierId;
    public String supName;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public Supplier(int supplierId, String supName) {
        this.supplierId = supplierId;
        this.supName = supName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Supplier)
            return this.supName == ((Supplier) obj).supName;
        return false;
    }

    @Override
    public String toString() {
        return supName;
    }
}
