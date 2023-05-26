package com.devstack.pos.entity;

public class ProductDetail implements SuperEntity {
    private String code;
    private String barCode;
    private int qtyOnHand;
    private double sellingPrice;
    private double showPrice;
    private double buyingPrice;
    private int productCode;
    private boolean discountAvailability;

    public ProductDetail(String code, String barCode, int qtyOnHand, double sellingPrice, double showPrice, double buyingPrice, int productCode, boolean discountAvailability) {
        this.code = code;
        this.barCode = barCode;
        this.qtyOnHand = qtyOnHand;
        this.sellingPrice = sellingPrice;
        this.showPrice = showPrice;
        this.buyingPrice = buyingPrice;
        this.productCode = productCode;
        this.discountAvailability = discountAvailability;
    }

    public ProductDetail() {
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(double showPrice) {
        this.showPrice = showPrice;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public boolean isDiscountAvailability() {
        return discountAvailability;
    }

    public void setDiscountAvailability(boolean discountAvailability) {
        this.discountAvailability = discountAvailability;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "code=" + code +
                ", barCode='" + barCode + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", sellingPrice=" + sellingPrice +
                ", showPrice=" + showPrice +
                ", buyingPrice=" + buyingPrice +
                ", productCode=" + productCode +
                ", discountAvailability=" + discountAvailability +
                '}';
    }
}
