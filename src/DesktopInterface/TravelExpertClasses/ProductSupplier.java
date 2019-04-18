package DesktopInterface.TravelExpertClasses;

public class ProductSupplier {

    private int productSupplierId;
    private int productId;
    private String productName;
    private String supplierName;
    private int supplierId;


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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ProductSupplier() {
    }

    public ProductSupplier(int productId, String productName, String supplierName, int supplierId) {
        //this.productSupplierId = productSupplierId;
        this.productId = productId;
        this.productName = productName;
        this.supplierName = supplierName;
        this.supplierId = supplierId;
    }

    @Override
    public String toString() {
        return productName;
    }
}
