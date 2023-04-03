
import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
import com.ddhn.services.BranchService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author truon
 */
public class BranchTester {

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
    public void testNotNull() {
        try {
            List<Branch> list = BranchService.getBranchs();

            long t = list.stream().filter(b -> b.getName() == null).count();
            Assertions.assertTrue(t == 0);

        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddBranch() {

        Branch b = new Branch("Nguyen Thi Nho", "30 Nguyen Thi Nho, Ho Chi Minh city");

        try {
            boolean actual = BranchService.addBranch(b);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM branch WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, b.getId());
            ResultSet rs = stm.executeQuery();

            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("Nguyen Thi Nho", rs.getString("name"));
            Assertions.assertEquals("30 Nguyen Thi Nho, Ho Chi Minh city", rs.getString("address"));

        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testDeleteBranch() {
        int id = 44;
        
        boolean actual;
        try {
            actual = BranchService.deleteBranch(44);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM branch WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testUpdateBranch() {
        Branch b = new Branch(34,"Test", "Test");
        boolean actual;
        try {
            actual = BranchService.updateBranch(b);
            Assertions.assertTrue(actual);
            
            String sql = "SELECT * FROM branch WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, b.getId());
            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals("Test", rs.getString("name"));
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testGetBranchById() {
        try {
            Branch b = BranchService.getBranchById(5);
            Assertions.assertEquals("Vo Van Tan", b.getName());
        } catch (SQLException ex) {
            Logger.getLogger(BranchTester.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    

}
