/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
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
public class BranchService {
    public static List<Branch> getBranchs() throws SQLException {
        List<Branch> branchs = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            
            ResultSet rs = stm.executeQuery("SELECT * FROM branch");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                branchs.add(new Branch(id ,name, address));
            }
        }
        return branchs;
    }
    
    public static boolean addBranch(Branch b) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO Branch(name, address) VALUES(?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, b.getName());
            stm.setString(2, b.getAddress());
            
            int r = stm.executeUpdate();
            
            return r > 0;
        }
    }
    
    public static boolean updateBranch(Branch b) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE branch SET name = ?, address = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, b.getName());
            stm.setString(2, b.getAddress());
            stm.setInt(3, b.getId());
            int r = stm.executeUpdate();   
            return r > 0;
        }
    }
    
    public static boolean deleteBranch(int branchId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM branch WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, branchId);
            int r = stm.executeUpdate();   
            return r > 0;
        }
    }
    
    public static Branch getBranchById(int branchId) throws SQLException {
        int id = 0;
        String name = null, address = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM branch WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, branchId);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                address = rs.getString("address");
            }
            return new Branch(id, name, address);
        }
    }
}
