package DesktopInterface.TravelExpertClasses;

public class PackageProductSupplier {

    private int packageId;
    private String productSupplierId;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(String productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public PackageProductSupplier(int packageId, String productSupplierId) {
        this.packageId = packageId;
        this.productSupplierId = productSupplierId;
    }

    @Override
    public String toString() {
        return "DesktopInterface.TravelExpertClasses.PackageProductSupplier{" +
                "packageId=" + packageId +
                ", productSupplierId='" + productSupplierId + '\'' +
                '}';
    }

}
