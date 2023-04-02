/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
import com.ddhn.pojo.Customer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Nome
 */
public class CustomerService {
    public static List<Customer> getCustomers() throws SQLException
    {
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = JdbcUtils.getConn())
        {
          Statement stm = conn.createStatement();
          ResultSet rs = stm.executeQuery("SELECT * FROM Customer");
          
          while(rs.next()){
            int CusId = rs.getInt("id");
            String CusName = rs.getString("name");
            String CusPhone = rs.getString("phone");
            Date CusBirthOfDate = rs.getDate("dateOfBirth");
            String CusPersonalID = rs.getString("personalID");
            
            customers.add(new Customer(CusId, CusName, CusPhone, CusBirthOfDate, CusPersonalID));
          }
        }
        return customers;
    }
    
    public static boolean addCustomer(Customer c) throws SQLException
    {
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "INSERT INTO customer(name, phone, dateOfBirth, personalID) VALUES(?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getCusName());
            stm.setString(2, c.getCusPhone());
            stm.setDate(3, c.getCusBirthOfDate());
            stm.setString(4, c.getCusPersonalID());
            int r = stm.executeUpdate();
            return r > 0;
        }
    }
    
    public static boolean updateCustomer(Customer c) throws SQLException
    {
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "UPDATE customer SET name = ?, phone = ?, dateOfBirth = ?, personalID = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getCusName());
            stm.setString(2, c.getCusPhone());
            stm.setDate(3, c.getCusBirthOfDate());
            stm.setString(4, c.getCusPersonalID());
            stm.setInt(5, c.getCusId());
            int r = stm.executeUpdate();
            return r > 0;
            
        }
    }
    
    public static boolean deleteCustomer(int customerID) throws SQLException
    {
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "DELETE FROM customer WHERE id =?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, customerID);
            int r = stm.executeUpdate();
            return r > 0;
        }
    }
    
    public static Customer getCustomerByID(int customerID) throws SQLException
    {
        Customer c = null;
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "SELECT * FROM customer WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, customerID);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                c = new Customer();
                c.setCusId(rs.getInt("id"));
                c.setCusName(rs.getString("name"));
                c.setCusPhone(rs.getString("phone"));
                c.setCusBirthOfDate(rs.getDate("dateOfBirth"));
                c.setCusPersonalID(rs.getString("personalID"));             
            }          
        }
        return c;
    }    
}
    
    
