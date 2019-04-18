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
    public String toString() {
        return supName;
    }
}
