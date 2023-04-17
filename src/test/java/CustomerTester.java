import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Customer;
import com.ddhn.services.CustomerService;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Nome
 */
public class CustomerTester {
    private static Connection conn;
    private static Customer c;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = Date.valueOf("2000-01-01");
    int id = 16;
    
    @BeforeAll
    public static void beforeAll(){
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddCustomer() throws ParseException{
        c = new Customer("Nguyen Van A", "0366004733", date, "0987654321");
        try {
            boolean actual = CustomerService.addCustomer(c);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM customer Where phone=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getCusPhone());
            ResultSet rs = stm.executeQuery();
            
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Nguyen Van A", rs.getString("name"));
            Assertions.assertEquals("0366004733", rs.getString("phone"));
            Assertions.assertEquals("2000-01-01", rs.getString("dateOfBirth"));
            Assertions.assertEquals("0987654321", rs.getString("personalID"));
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test 
    public void testUpdateCustomer() throws SQLException{
        c = new Customer(id, "TesterUpdate", "0366004733", date, "0987654321");
        try {   
            boolean actual = CustomerService.updateCustomer(c);
            Assertions.assertTrue(actual);
            String sql = "SELECT * FROM customer WHERE phone=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getCusPhone());
            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("TesterUpdate", rs.getString("name"));
            Assertions.assertEquals("0366004733", rs.getString("phone"));
            Assertions.assertEquals("2000-01-01", rs.getString("dateOfBirth"));
            Assertions.assertEquals("0987654321", rs.getString("personalID"));
            
        
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    @Test
    public void testDeleteCustomer(){
        try {
            boolean actual = CustomerService.deleteCustomer(id);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM customer WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            
            Assertions.assertFalse(rs.next());
 
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}