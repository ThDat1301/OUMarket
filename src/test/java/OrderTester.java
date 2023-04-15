/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Order;
import com.ddhn.pojo.OrderDetails;
import com.ddhn.services.OrderService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 *
 * @author truon
 */
public class OrderTester {
    private static Connection conn;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void afterAll() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddOrderWithCustomer() throws SQLException {
        // Arrange
        Date date = new Date(123, 4, 14);

        Order o = new Order(2000, date , 300000, 300001,2, 2);

        List<OrderDetails> listOd = new ArrayList<>();
        listOd.add(new OrderDetails(1, 2));
        listOd.add(new OrderDetails(2, 3));
        
        int result = OrderService.addOrder(o, listOd, true);
        
        Assertions.assertTrue(result != -1);
        
        String sql = "SELECT * FROM oumarketdb.order WHERE id=?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, result);
        ResultSet rs = stm.executeQuery();

        Assertions.assertNotNull(rs.next());
        Assertions.assertEquals(result, rs.getInt("id"));

    }
    
    @Test
    public void testAddOrderWithoutCustomer() throws SQLException {
        // Arrange
        Date date = new Date(123, 4, 15);

        Order o = new Order(2000, date , 150000, 200000, 2);

        List<OrderDetails> listOd = new ArrayList<>();
        listOd.add(new OrderDetails(2, 3));
        listOd.add(new OrderDetails(3, 4));
        
        int result = OrderService.addOrder(o, listOd, false);
        
        Assertions.assertTrue(result != -1);
        
        String sql = "SELECT * FROM oumarketdb.order WHERE id=?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, result);
        ResultSet rs = stm.executeQuery();

        Assertions.assertNotNull(rs.next());
        Assertions.assertEquals(result, rs.getInt("id"));

    }
}
