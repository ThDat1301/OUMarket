/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Product;
import com.ddhn.services.ProductService;
import com.ddhn.utils.MessageBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author truon
 */

public class FXMLProductController implements Initializable {
    @FXML private TextField txtName;
    @FXML private TextField txtOrigin;
    @FXML private TextField txtPrice;
    @FXML private TextField txtDiscountPrice;
    @FXML private RadioButton rdAvaiYes;
    @FXML private RadioButton rdAvaiNo;
    @FXML private Button btnInsert;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private TableView tbProduct;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, String> colOrigin;
    @FXML private TableColumn<Product, Float> colPrice;
    @FXML private TableColumn<Product, Float> colDiscountPrice;
    @FXML private TableColumn<Product, Boolean> colActive;
    @FXML private ToggleGroup tgAvai;

    int index = -1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            renewTable();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void clearInput() {
        txtName.clear();
        txtOrigin.clear();
        txtPrice.clear();
        txtDiscountPrice.clear();
        rdAvaiYes.setSelected(true);
    }
    
    public void renewTable() throws SQLException {
        this.tbProduct.getItems().clear();
        this.tbProduct.getColumns().clear();
        colId = new TableColumn("ID");
        colName = new TableColumn("Name");
        colOrigin = new TableColumn("Origin");
        colPrice = new TableColumn("Price");
        colDiscountPrice = new TableColumn("Discount Price");
        colActive = new TableColumn("Active");
        
        colId.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<Product, String>("origin"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        colDiscountPrice.setCellValueFactory(new PropertyValueFactory<Product, Float>("discountPrice"));
        colActive.setCellValueFactory(new PropertyValueFactory<Product, Boolean>("active"));
        
        this.tbProduct.getColumns().addAll(colId, 
                colName,
                colOrigin,
                colPrice,
                colDiscountPrice,
                colActive);
        List<Product> p = ProductService.getProducts();
        this.tbProduct.setItems(FXCollections.observableArrayList(p));
        
//        ObservableList<Product> products = null;
//        try {
//            products = FXCollections.observableArrayList(ProductService.getProducts());
//        } catch (SQLException ex) {
//            Logger.getLogger(FXMLProductController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        tbProduct.setItems(products);
    }
    
    public void getSelected() {
        index = tbProduct.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtName.setText(colName.getCellData(index));
        txtOrigin.setText(colOrigin.getCellData(index));
        txtPrice.setText(colPrice.getCellData(index).toString());
        txtDiscountPrice.setText(colDiscountPrice.getCellData(index).toString());
        if (colActive.getCellData(index) == true) 
            rdAvaiYes.setSelected(true);
        else rdAvaiNo.setSelected(true);
    }
    
    public void addProduct(ActionEvent e) throws SQLException {
        if(txtName.getText().trim().isEmpty() || txtOrigin.getText().trim().isEmpty() ||
                txtPrice.getText().trim().isEmpty() || tgAvai.getSelectedToggle() == null) {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();
        }
        else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, origin;
                float price, discountPrice;
                boolean active;
                
                name = txtName.getText();
                origin = txtOrigin.getText();
                price = Float.parseFloat(txtPrice.getText());
                discountPrice = Float.parseFloat(txtDiscountPrice.getText());
                active = rdAvaiYes.isSelected();
                    
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to insert this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    ProductService.addProduct(new Product(name, origin, price, discountPrice, active));
                    MessageBox.getBox("Success", "Inserted successfully!!!", Alert.AlertType.INFORMATION).show();
                    renewTable();
                    clearInput();
                }
                else if (result.get() == ButtonType.CANCEL) {

                }
            }
        }
    }
    
    public void updateProduct(ActionEvent e) throws SQLException {
        if(txtName.getText().trim().isEmpty() || txtOrigin.getText().trim().isEmpty() ||
                txtPrice.getText().trim().isEmpty() || tgAvai.getSelectedToggle() == null) {
            MessageBox.getBox("Error", "Please complete all fields before update!!!", Alert.AlertType.ERROR).show();
        }
        else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, origin;
                float price, discountPrice;
                boolean active;
                int id;
                
                name = txtName.getText();
                origin = txtOrigin.getText();
                price = Float.parseFloat(txtPrice.getText());
                discountPrice = Float.parseFloat(txtDiscountPrice.getText());
                active = rdAvaiYes.isSelected();

                Product p = (Product) tbProduct.getItems().get(index);
                id = p.getId();
                                    
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to update this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    ProductService.updateProduct(new Product(id ,name, origin, price, discountPrice, active));
                    MessageBox.getBox("Success", "Updated successfully!!!", Alert.AlertType.INFORMATION).show();
                    renewTable();
                    clearInput();
                }
                else if (result.get() == ButtonType.CANCEL) {

                }
            }
        }
    }
    
    public void deleteProduct(ActionEvent e) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Product p = (Product) tbProduct.getItems().get(index);
            int id = p.getId();
            Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to delete this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    ProductService.deleteProduct(id);
                    MessageBox.getBox("Success", "Deleted successfully!!!", Alert.AlertType.INFORMATION).show();
                    renewTable();
                    clearInput();
                }
                else if (result.get() == ButtonType.CANCEL) {

                }
        }
    }
}
