/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author truon
 */
public class EmployeeService {
    public static List<Employee> getEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            
            ResultSet rs = stm.executeQuery("SELECT * FROM employee");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int branchId = rs.getInt("branch_id");
                
                employees.add(new Employee(id, name, phone, username, password, branchId));
            }   
        }
        return employees;
    }
    
    public static boolean addEmployee(Employee e) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO Employee(name, phone, username, password, branch_id) VALUES(?, ?, ?, ?, ?)";
            e.setPassword(e.getPassword());
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getPhone());
            stm.setString(3, e.getUsername());
            stm.setString(4, e.getPassword());
            stm.setInt(5, e.getBranch_id());      
            
            int r = stm.executeUpdate();
            
            return r > 0;
        }
    }
    
    public static boolean updateEmployee(Employee e) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE employee SET name = ?, phone = ?, username = ?, password = ?, branch_id = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, e.getName());
            stm.setString(2, e.getPhone());
            stm.setString(3, e.getUsername());
            stm.setString(4, e.getPassword());
            stm.setInt(5, e.getBranch_id());  
            stm.setInt(6, e.getId());
            int r = stm.executeUpdate();   
            return r > 0;
        }
    }
    
    public static boolean deleteEmployee(int employeeId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM employee WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, employeeId);
            int r = stm.executeUpdate();   
            return r > 0;
        }
    }
    
    public static int getIdByName(String username) throws SQLException {
        int id = 0;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM employee WHERE username = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
        return id;
        }
    }
    
    public static Employee getEmpById(int idEmpl) throws SQLException {
        int id = 0, branch_id = 0;
        String name = null, phone = null, username = null, password = null;
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM employee WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idEmpl);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                phone = rs.getString("phone");
                username = rs.getString("username");
                password = rs.getString("password");
                branch_id = rs.getInt("branch_id");
            }
        }
        return new Employee(id, name, phone, username, password, branch_id);
    } 
}
