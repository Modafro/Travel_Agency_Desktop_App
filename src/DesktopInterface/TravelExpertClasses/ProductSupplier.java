package DesktopInterface.TravelExpertClasses;

public class ProductSupplier {

    public int productSupplierId;
    public int productId;
    public int supplierId;

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public ProductSupplier(int productSupplierId, int productId, int supplierId) {
        this.productSupplierId = productSupplierId;
        this.productId = productId;
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return "DesktopInterface.TravelExpertClasses.ProductSupplier{" +
                "productSupplierId=" + productSupplierId +
                ", productId=" + productId +
                ", supplierId=" + supplierId +
                '}';
    }
}
