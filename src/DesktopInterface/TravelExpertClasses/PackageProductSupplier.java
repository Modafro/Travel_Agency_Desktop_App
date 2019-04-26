/**
 * Author: James Sharpe
 * Date: April 2019
 * Objective: PackageProductSupplier Class for storing and retrieving PackageProductSupplier information
 */



package DesktopInterface.TravelExpertClasses;

public class PackageProductSupplier {

    private int packageId;
    private int productSupplierId;
    private String prodName;
    private String suppName;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getSuppName() {
        return suppName;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }

    public PackageProductSupplier() {
    }

    public PackageProductSupplier(int productSupplierId, String prodName, String suppName) {
        this.productSupplierId = productSupplierId;
        this.prodName = prodName;
        this.suppName = suppName;
    }

    @Override
    public String toString() {
        return suppName + ", " + prodName;
    }

}
