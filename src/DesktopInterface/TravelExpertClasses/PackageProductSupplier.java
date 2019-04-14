package DesktopInterface.TravelExpertClasses;

public class PackageProductSupplier {

    private int packageId;
    private int productSupplierId;

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

    public PackageProductSupplier() {
    }

    @Override
    public String toString() {
        return "DesktopInterface.TravelExpertClasses.PackageProductSupplier{" +
                "packageId=" + packageId +
                ", productSupplierId='" + productSupplierId + '\'' +
                '}';
    }

}
