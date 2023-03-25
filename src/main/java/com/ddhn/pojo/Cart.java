/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

/**
 *
 * @author truon
 */
public class Cart {
    private int productId;
    private String productName;
    private float productPrice;
    private float productDiscountPrice;
    private float productQuantity;
    private float productAmount;

    public Cart() {
    }

    public Cart(int productId, String productName, float productPrice, float productDiscountPrice, float productQuantity, float productAmount) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.productQuantity = productQuantity;
        this.productAmount = productAmount;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productPrice
     */
    public float getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the productDiscountPrice
     */
    public float getProductDiscountPrice() {
        return productDiscountPrice;
    }

    /**
     * @param productDiscountPrice the productDiscountPrice to set
     */
    public void setProductDiscountPrice(float productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    /**
     * @return the productQuantity
     */
    public float getProductQuantity() {
        return productQuantity;
    }

    /**
     * @param productQuantity the productQuantity to set
     */
    public void setProductQuantity(float productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * @return the productAmount
     */
    public float getProductAmount() {
        return productAmount;
    }

    /**
     * @param productAmount the productAmount to set
     */
    public void setProductAmount(float productAmount) {
        this.productAmount = productAmount;
    }


    
    
}
