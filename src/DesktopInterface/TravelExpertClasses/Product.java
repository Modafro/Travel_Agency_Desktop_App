package DesktopInterface.TravelExpertClasses;

public class Product {

    public int productId;
    public String prodName;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Product(int productId, String prodName) {
        this.productId = productId;
        this.prodName = prodName;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "DesktopInterface.TravelExpertClasses.Product{" +
                "productId=" + productId +
                ", prodName='" + prodName + '\'' +
                '}';
    }
}
