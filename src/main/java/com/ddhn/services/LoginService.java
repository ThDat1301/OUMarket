/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author truon
 */
public class LoginService {

    public static int Login(String username, String password) throws SQLException {
        password = DigestUtils.md5Hex(password);
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM employee WHERE username= ?");
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                if (rs.getString("username").equals(username) && rs.getString("password").equals(password)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static String getBranchByUsername(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT b.name FROM oumarketdb.employee as e, oumarketdb.branch as b WHERE e.branch_id = b.id AND e.username =\"" + username + "\"");
            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return "";
    }
    
    
    
    
}
