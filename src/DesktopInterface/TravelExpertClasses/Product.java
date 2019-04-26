/**
 * Author: James Sharpe
 * Date: April 2019
 * Objective: Product Class for storing and retrieving product
 */



package DesktopInterface.TravelExpertClasses;

public class Product {

    private int productId;
    private String prodName;

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
    public boolean equals(Object obj) {
        if(obj instanceof Product)
            return this.productId == ((Product) obj).productId;
        return false;
    }

    @Override
    public String toString() {
        return prodName;
    }
}
