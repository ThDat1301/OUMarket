/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

/**
 *
 * @author truon
 */
public class OrderDetails {
    private int id;
    private float quantity;
    private int order_id;
    private int product_id;

    public OrderDetails() {
    }

    public OrderDetails(int id, float quantity, int order_id, int product_id) {
        this.id = id;
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_id = product_id;
    }
    
    public OrderDetails(float quantity, int order_id, int product_id) {
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public OrderDetails(float quantity, int product_id) {
        this.quantity = quantity;
        this.product_id = product_id;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the quantity
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the order_id
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    
    
    
}
