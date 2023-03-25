/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

/**
 *
 * @author truon
 */
public class Product {
    private int id;
    private String name;
    private String origin;
    private float price;
    private float discountPrice; 
    private boolean active;

    public Product(int id, String name, String origin, float price, float discountPrice, boolean active) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.discountPrice = discountPrice;
        this.active = active;
    }
    
    public Product(String name, String origin, float price, float discountPrice, boolean active) {
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.discountPrice = discountPrice;
        this.active = active;
    }

    public Product() {
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the discountPrice
     */
    public float getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.id);
    }
   
    
    
    
}
