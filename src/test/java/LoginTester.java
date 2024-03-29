
import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Employee;
//import com.ddhn.pojo.Customer;
import com.ddhn.services.LoginService;
import com.ddhn.services.EmployeeService;
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
import org.apache.commons.codec.digest.DigestUtils;
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
public class LoginTester {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll(){
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(LoginTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @AfterAll
    public static void afterAll(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testLogin() throws SQLException {
        Employee e = new Employee("Le Minh Duc", "0123457893", "minhduc", "ducprotc123", 6, 1);
        Employee actual = LoginService.Login(e.getUsername(), e.getPassword());
        Assertions.assertEquals(e.getUsername(), actual.getUsername());
    }
    
    @Test
    public void testGetBranchByUsername() throws SQLException {
        String expected = "Le Quang Dinh";
        String username = "minhduc";
        
        String actual = LoginService.getBranchByUsername(username);
        Assertions.assertEquals(expected, actual);
    }
}
