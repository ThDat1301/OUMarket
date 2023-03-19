/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author truon
 */
public class ProductService {
    public static List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM product");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String origin = rs.getString("origin");
                float price = rs.getFloat("price");
                float discountPrice = rs.getFloat("discountPrice");
                boolean active = rs.getBoolean("active");
                
                products.add(new Product(id, name, origin, price, discountPrice, active));
            }
        }
        return products;
    }
    
    public static boolean addProduct(Product p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO product(name, origin, price, discountPrice, active) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setString(2, p.getOrigin());
            stm.setFloat(3, p.getPrice());
            stm.setFloat(4, p.getDiscountPrice());
            stm.setBoolean(5, p.isActive());
            
            int r = stm.executeUpdate();
            return r > 0;
           
        }
    }
    
    public static boolean updateProduct(Product p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE product SET name=?, origin=?, price=?, discountPrice=?, active=? WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setString(2, p.getOrigin());
            stm.setFloat(3, p.getPrice());
            stm.setFloat(4, p.getDiscountPrice());
            stm.setBoolean(5, p.isActive());
            stm.setInt(6, p.getId());
            
            int r = stm.executeUpdate();
            return r > 0; 
        }
    }
    
    public static boolean deleteProduct(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM product  WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            
            int r = stm.executeUpdate();
            return r > 0;
        }
    }
}
