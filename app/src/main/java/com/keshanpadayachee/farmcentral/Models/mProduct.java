package com.keshanpadayachee.farmcentral.Models;

import java.io.Serializable;

public class mProduct implements Serializable {

    // Attributes of a Product
    private int productId;
    private String farmerId;
    private String productName;
    private String productDescription;
    private int productQuantity;
    private double productPrice;
    private String productType;

    public mProduct() {
        // EMPTY CONSTRUCTOR DO NOT REMOVE
    }

    // Product constructor that accepts parameters
    public mProduct(int productId, String farmerId, String productName, String productDescription, int productQuantity, double productPrice, String productType) {
        this.productId = productId;
        this.farmerId = farmerId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    // Constructor for adding a product
    public mProduct(String farmerId, String productName, String productDescription, int productQuantity, double productPrice, String productType) {
        this.farmerId = farmerId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productType = productType;
    }

    // Getters and setters
    public int getProductID() {
        return productId;
    }

    public void setProductID(int productID) {
        this.productId = productId;
    }

    public String getFarmerID() {
        return farmerId;
    }

    public void setFarmerID(String farmerID) {
        this.farmerId = farmerID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
