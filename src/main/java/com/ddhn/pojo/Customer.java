/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.pojo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 *
 * @author Nome
 */
public class Customer {
    private int CusId;
    private String CusName;
    private String CusPhone;
    private Date CusBirthOfDate;
    private String CusPersonalID;
    
    
    public Customer(){}
    public Customer(int id, String name, String phone, Date dateOfBirth, String personalID)
    {
        this.CusId = id;
        this.CusName = name;
        this.CusPhone = phone;
        this.CusBirthOfDate = dateOfBirth;
        this.CusPersonalID = personalID;
    }
    
    public Customer(String name, String phone, Date dateOfBirth, String personalID)
    {
        this.CusName = name;
        this.CusPhone = phone;
        this.CusBirthOfDate = dateOfBirth;
        this.CusPersonalID = personalID;
    }
    
    public Customer(String name, String phone, String dateOfBirth, String personalID)
    {
        this.CusName = name;
        this.CusPhone = phone;
        this.CusPersonalID = personalID;
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.CusBirthOfDate = (Date) formatter.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the CusId
     */
    public int getCusId() {
        return CusId;
    }

    /**
     * @param CusId the CusId to set
     */
    public void setCusId(int CusId) {
        this.CusId = CusId;
    }

    /**
     * @return the CusName
     */
    public String getCusName() {
        return CusName;
    }

    /**
     * @param CusName the CusName to set
     */
    public void setCusName(String CusName) {
        this.CusName = CusName;
    }

    /**
     * @return the CusPhone
     */
    public String getCusPhone() {
        return CusPhone;
    }

    /**
     * @param CusPhone the CusPhone to set
     */
    public void setCusPhone(String CusPhone) {
        this.CusPhone = CusPhone;
    }

    /**
     * @return the CusBirthOfDate
     */
    public Date getCusBirthOfDate() {
        return CusBirthOfDate;
    }

    /**
     * @param CusBirthOfDate the CusBirthOfDate to set
     */
    public void setCusBirthOfDate(Date CusBirthOfDate) {
        this.CusBirthOfDate = CusBirthOfDate;
    }

    /**
     * @return the CusPersonalID
     */
    public String getCusPersonalID() {
        return CusPersonalID;
    }

    /**
     * @param CusPersonalID the CusPersonalID to set
     */
    public void setCusPersonalID(String CusPersonalID) {
        this.CusPersonalID = CusPersonalID;
    }
    
    
    
}
