/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Product;
import com.ddhn.services.ProductService;
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
/**
 *
 * @author truon
 */
public class ProductTester {
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
    public void testAddProduct() throws SQLException  {
        Product p = new Product("testProduct", "testO", 10000, 0, true);
        int productId = ProductService.addProduct(p);
        
        Assertions.assertTrue(productId != -1);
        
        String sql = "SELECT * FROM oumarketdb.product WHERE id=?";
        PreparedStatement stm = conn.prepareCall(sql);
        stm.setInt(1, productId);
        ResultSet rs = stm.executeQuery();

        Assertions.assertNotNull(rs.next());
        Assertions.assertEquals(productId, rs.getInt("id"));
        
    }
    
    @Test
    public void testAddDupProduct() throws SQLException  {
        Product p = new Product("Tomato", "Vietnam", 10000, 0, true);
        int productId = ProductService.addProduct(p);
        
        Assertions.assertTrue(productId == -1);
    }
    
    @Test
        public void testAddNullProduct() throws SQLException {
        int result = ProductService.addProduct(null);
        Assertions.assertTrue(result == -1);
    }
    
    @Test
    public void testGetProductById() throws SQLException {
        int id = 1;
        Product p = ProductService.getProductById(id);
        Assertions.assertEquals("Red Carrot", p.getName());
    }
}
