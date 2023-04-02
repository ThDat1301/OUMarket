/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.services;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Order;
import com.ddhn.pojo.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author truon
 */
public class OrderService {
    public static boolean addOrder(Order o, int cusId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "INSERT INTO order (id, date, totalPrice, moneyCus, customer_id, employee_id) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, o.getId());
            stm.setDate(2, o.getDate());
            stm.setFloat(3, o.getTotalPrice());
            stm.setFloat(4, o.getMoneyCus());
            stm.setInt(4, cusId);
            stm.setInt(5, o.getEmployeeId());
            
            int r = stm.executeUpdate();
            return r > 0;
        }
    }
    public static int addOrder(Order o, List<OrderDetails> listOd) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            int orderId = -1;
            
            String sql = "INSERT INTO oumarketdb.order (date, totalPrice, moneyCus, employee_id) VALUES(?, ?, ?, ?)";
            String sql1 = "INSERT INTO oumarketdb.order_detail (quantity, order_id, product_id) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setDate(1, o.getDate());
            stm.setFloat(2, o.getTotalPrice());
            stm.setFloat(3, o.getMoneyCus());
            stm.setInt(4, o.getEmployeeId());
            
            stm.executeUpdate();
            try (ResultSet key = stm.getGeneratedKeys()) {
                if (key.next()) {
                    orderId = key.getInt(1);
                }
                else {
                    conn.rollback();
                }
            }
            stm.close();
            for (OrderDetails od : listOd) {
                stm = conn.prepareStatement(sql1);
                stm.setFloat(1, od.getQuantity());
                stm.setInt(2,orderId);
                stm.setInt(3, od.getProduct_id());
                stm.executeUpdate();
                stm.close();
            }
            return -1;
        }
    }
}
