/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

import java.sql.Date;

/**
 *
 * @author truon
 */
public class Order {
    private int id;
    private Date date;
    private float totalPrice;
    private float moneyCus;
    private int customerId;
    private int employeeId;

    public Order(int id, Date date, float totalPrice, float moneyCus, int customerId, int employeeId) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.moneyCus = moneyCus;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }
    
    public Order(int id, Date date, float totalPrice, float moneyCus, int employeeId) {
        this.id = id;
        this.date = date;
        this.totalPrice = totalPrice;
        this.moneyCus = moneyCus;
        this.employeeId = employeeId;
    }
    
     public Order(Date date, float totalPrice, float moneyCus, int employeeId) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.moneyCus = moneyCus;
        this.employeeId = employeeId;
    }
    
    public Order(Date date, float totalPrice, float moneyCus, int customerId, int employeeId) {
        this.date = date;
        this.totalPrice = totalPrice;
        this.moneyCus = moneyCus;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the totalPrice
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the moneyCus
     */
    public float getMoneyCus() {
        return moneyCus;
    }

    /**
     * @param moneyCus the moneyCus to set
     */
    public void setMoneyCus(float moneyCus) {
        this.moneyCus = moneyCus;
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the employeeId
     */
    public int getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    
    
}
