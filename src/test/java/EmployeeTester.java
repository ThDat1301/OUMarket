
import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Employee;
import com.ddhn.services.EmployeeService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * @author truon
 */
public class EmployeeTester {
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
    public void testAddEmployee() {
        Employee e = new Employee("Tester01", "0123456789", "test1", "test1", 5);
        try {
            boolean actual = EmployeeService.addEmployee(e);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM employee WHERE name=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, e.getName());
            ResultSet rs = stm.executeQuery();

            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Tester01", rs.getString("name"));
            Assertions.assertEquals("0123456789", rs.getString("phone"));
            Assertions.assertEquals("test1", rs.getString("username"));

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUpdateEmployee() {
        Employee e = new Employee(18, "Tester1", "123", "abc", "123", 2, 0);
        try {
            boolean actual = EmployeeService.updateEmployee(e);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM employee WHERE name=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, e.getName());
            ResultSet rs = stm.executeQuery();

            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Tester1", rs.getString("name"));
            Assertions.assertEquals(2, rs.getInt("branch_id"));
            
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testDeleteEmployee() {
        int id = 15;
        try {
            boolean actual = EmployeeService.deleteEmployee(id);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM employee WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
