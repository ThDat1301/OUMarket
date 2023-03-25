/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Cart;
import com.ddhn.pojo.Order;
import com.ddhn.pojo.OrderDetails;
import com.ddhn.pojo.Product;
import com.ddhn.services.EmployeeService;
import com.ddhn.services.OrderService;
import com.ddhn.services.ProductService;
import com.ddhn.utils.MessageBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLPurchaseController implements Initializable {
    @FXML private DatePicker dpDate;
    @FXML private ComboBox cbCustomer;
    @FXML private ComboBox<Product> cbProduct;
    @FXML private TextField txtProductName;
    @FXML private TextField txtProductPrice;
    @FXML private TextField txtProductDiscountPrice;
    @FXML private TextField txtQuantity;
    @FXML private TextField txtAmmount;
    @FXML private Button btnAddToCart;
    @FXML private TextField txtTotalAmount;
    @FXML private TextField txtCustomerMoney;
    @FXML private Button btnSubmit;
    @FXML private TableView<Cart> tbProduct;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            loadProduct();
            loadTableColumns();
            this.cbProduct.getSelectionModel().selectFirst();
            onChangeProduct();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtQuantity.textProperty().addListener(e -> {
            onChangeQuantity();
        });

        this.cbProduct.setOnAction(e -> {
            try {
                onChangeProduct();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLPurchaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    }   
    
    public void loadProduct() throws SQLException {
        
        List<Product> list = ProductService.getProducts();
        cbProduct.setItems(FXCollections.observableList(list));
    }
    
    public void onChangeProduct() throws SQLException {
           
        int id = cbProduct.getSelectionModel().getSelectedItem().getId();
        String name = cbProduct.getSelectionModel().getSelectedItem().getName();
        float price = cbProduct.getSelectionModel().getSelectedItem().getPrice();
        float discountPrice = cbProduct.getSelectionModel().getSelectedItem().getDiscountPrice();

        txtQuantity.setText("1");

        float quantity = Float.parseFloat(txtQuantity.getText());
        txtProductName.setText(name);
        txtProductPrice.setText(String.valueOf(price));
        txtProductDiscountPrice.setText(String.valueOf(discountPrice));
        if(discountPrice != 0) {
            txtAmmount.setText(String.valueOf(discountPrice * quantity));
        }
        else txtAmmount.setText(String.valueOf(price * quantity));

    }
    
    private void loadTableColumns() {
             
        TableColumn colProductId = new TableColumn("ID");
        colProductId.setCellValueFactory(new PropertyValueFactory("productId"));
        colProductId.setPrefWidth(30);

        TableColumn colProductName = new TableColumn("Name");
        colProductName.setCellValueFactory(new PropertyValueFactory("productName"));
        colProductName.setPrefWidth(200);

        TableColumn colProductPrice = new TableColumn("Price");
        colProductPrice.setCellValueFactory(new PropertyValueFactory("productPrice"));
        colProductPrice.setPrefWidth(65);
        
        TableColumn colProductDiscountPrice = new TableColumn("Discount price");
        colProductDiscountPrice.setCellValueFactory(new PropertyValueFactory("productDiscountPrice"));
        colProductDiscountPrice.setPrefWidth(100);
        
        TableColumn colQuantity = new TableColumn("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory("productQuantity"));
        colQuantity.setPrefWidth(75);
        
        TableColumn colAmount = new TableColumn("Amount");
        colAmount.setCellValueFactory(new PropertyValueFactory("productAmount"));
        colAmount.setPrefWidth(70);
        
                
        this.tbProduct.getColumns().addAll(colProductId,colProductName, colProductPrice,
                colProductDiscountPrice, colQuantity, colAmount);
        addButtonToTable();
    }
    
    private void addButtonToTable() {
        TableColumn<Cart, Void> colBtn = new TableColumn();

        Callback<TableColumn<Cart, Void>, TableCell<Cart, Void>> cellFactory = new Callback<TableColumn<Cart, Void>, TableCell<Cart, Void>>() {
            @Override
            public TableCell<Cart, Void> call(final TableColumn<Cart, Void> param) {
                final TableCell<Cart, Void> cell = new TableCell<Cart, Void>() {

                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Cart data = getTableView().getItems().get(getIndex());
                            tbProduct.getItems().remove(data);
                            txtTotalAmount.setText(String.valueOf(getTotalAmount()));
                            
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tbProduct.getColumns().add(colBtn);

    }
    
    private void onChangeQuantity() {
        float price = Float.parseFloat(txtProductPrice.getText());
        float discountPrice = Float.parseFloat(txtProductDiscountPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        if (discountPrice != 0) {
            txtAmmount.setText(String.valueOf(discountPrice * quantity));
        }
        else txtAmmount.setText(String.valueOf(price * quantity));
    }
    
    public void addToCart(ActionEvent e) {
        int id = cbProduct.getSelectionModel().getSelectedItem().getId();
        String name = cbProduct.getSelectionModel().getSelectedItem().getName();
        float price = cbProduct.getSelectionModel().getSelectedItem().getPrice();
        float discountPrice = cbProduct.getSelectionModel().getSelectedItem().getDiscountPrice();
        float quantity = Float.parseFloat(txtQuantity.getText());
        float amount = Float.parseFloat(txtAmmount.getText());
        
        ObservableList<Cart> list = tbProduct.getItems();
        Cart c = checkDup(list, id);
        if (c != null) {
            quantity += c.getProductQuantity();
            amount += c.getProductAmount();
            c.setProductQuantity(quantity);
            c.setProductAmount(amount);
            tbProduct.refresh();
        }
        else {
            Cart item = new Cart(id, name, price, discountPrice, quantity, amount);
            tbProduct.getItems().add(item);
        }
        txtTotalAmount.setText(String.valueOf(getTotalAmount()));
        
    }
    
    public Cart checkDup(ObservableList<Cart> list, int id) {
        for(Cart c : list) {
            if (c.getProductId() == id) {
                return c;
            }
        }
        return null;
    }
    
    public float getTotalAmount() {
        float totalAmount = 0;
        ObservableList<Cart> list = tbProduct.getItems();
        for(Cart item : list) {
            totalAmount += item.getProductAmount();
        }
        return totalAmount;
    }
    
    public void addOrder(ActionEvent e) throws SQLException {
        if (dpDate.getValue() != null && txtTotalAmount.getText() != null && txtCustomerMoney.getText() != null) {
            if (Float.parseFloat(txtCustomerMoney.getText()) >= Float.parseFloat(txtTotalAmount.getText())) {
                try (Connection conn = JdbcUtils.getConn()) {
                    Date date;
                    float totalPrice, moneyCustomer;
                    int employeeId;
                    ObservableList<Cart> cart = tbProduct.getItems();
                    List<OrderDetails> listOd = new ArrayList<>();
                    for (Cart c : cart) {
                        OrderDetails od = new OrderDetails(c.getProductQuantity(), c.getProductId());
                        listOd.add(od);
                    }

                    date = Date.valueOf(dpDate.getValue());
                    totalPrice = Float.parseFloat(txtTotalAmount.getText());
                    moneyCustomer = Float.parseFloat(txtCustomerMoney.getText());
                    employeeId = FXMLLoginController.currentEmployeeId;

                    Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to checkout?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                    if (result.get() == ButtonType.OK) {
                        OrderService.addOrder(new Order(date, totalPrice, moneyCustomer, employeeId), listOd);
                        MessageBox.getBox("Success", "Checkout successfully!!!", Alert.AlertType.INFORMATION).show();
                    }
                    else if (result.get() == ButtonType.CANCEL) {
                        
                    }  
                }
            }
            else MessageBox.getBox("Error", 
            "Please input Customer's money greater or equal than Total Amount", 
            Alert.AlertType.ERROR).show();
        }
        else MessageBox.getBox("Error", "Please complete all fields before checkout!!!", Alert.AlertType.ERROR).show();

        
        
    }
    
    
}
