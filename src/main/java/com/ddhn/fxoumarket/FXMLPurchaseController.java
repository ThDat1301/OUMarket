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
import com.ddhn.services.CustomerService;
import com.ddhn.utils.MessageBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLPurchaseController implements Initializable {

    @FXML
    private DatePicker dpDate;
//    @FXML private ComboBox cbCustomer;
    @FXML
    private ComboBox<Product> cbProduct;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductPrice;
    @FXML
    private TextField txtProductDiscountPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtAmmount;
    @FXML
    private Button btnAddToCart;
    @FXML
    private TextField txtTotalAmount;
    @FXML
    private TextField txtCustomerMoney;
    @FXML
    private Button btnSubmit;
    @FXML
    private TableView<Cart> tbProduct;

    @FXML
    private TextField txtcusID;
    @FXML
    private Label lbDate;
    @FXML
    private Label lbCusName;
    @FXML
    private Label lbCusDate;
    @FXML
    private HBox root;
    private float voucher = 1.f;
    private boolean checkCus = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.lbDate.setText(getLocalDate());
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
        if (discountPrice != 0) {
            txtAmmount.setText(String.valueOf(discountPrice * quantity));
        } else {
            txtAmmount.setText(String.valueOf(price * quantity));
        }

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

        this.tbProduct.getColumns().addAll(colProductId, colProductName, colProductPrice,
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
                            checkVoucher();
                            txtTotalAmount.setText(String.valueOf(getTotalAmount() * voucher));

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
        } else {
            txtAmmount.setText(String.valueOf(price * quantity));
        }
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
        } else {
            Cart item = new Cart(id, name, price, discountPrice, quantity, amount);
            tbProduct.getItems().add(item);
        }
        checkVoucher();
        txtTotalAmount.setText(String.valueOf(getTotalAmount() * voucher));

    }

    public Cart checkDup(ObservableList<Cart> list, int id) {
        for (Cart c : list) {
            if (c.getProductId() == id) {
                return c;
            }
        }
        return null;
    }

    public float getTotalAmount() {
        float totalAmount = 0;
        ObservableList<Cart> list = tbProduct.getItems();
        for (Cart item : list) {
            totalAmount += item.getProductAmount();
        }
        return totalAmount;
    }

    public void addOrder(ActionEvent e) throws SQLException {
        if (!txtTotalAmount.getText().trim().isEmpty() && !txtCustomerMoney.getText().trim().isEmpty()) {
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

                    totalPrice = Float.parseFloat(txtTotalAmount.getText());
                    moneyCustomer = Float.parseFloat(txtCustomerMoney.getText());
                    employeeId = FXMLLoginController.currentEmployeeId;
                    LocalDate localDate = LocalDate.now();
                    date = Date.valueOf(localDate);

                    Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to checkout?",
                            Alert.AlertType.CONFIRMATION).showAndWait();
                    if (result.get() == ButtonType.OK) {
                        if (checkCus) {
                            int customerId = Integer.parseInt(txtcusID.getText());
                            OrderService.addOrder(new Order(date, totalPrice, moneyCustomer, customerId, employeeId), listOd, checkCus);
                            MessageBox.getBox("Success", "Checkout successfully!!!", Alert.AlertType.INFORMATION).show();
                            checkCus = false;
                        }
                        else {
                            OrderService.addOrder(new Order(date, totalPrice, moneyCustomer, employeeId), listOd, checkCus);
                            MessageBox.getBox("Success", "Checkout successfully!!!", Alert.AlertType.INFORMATION).show();
                        }
                        reset();
                    } else if (result.get() == ButtonType.CANCEL) {

                    }
                }
            } else {
                MessageBox.getBox("Error",
                        "Please input Customer's money greater or equal than Total Amount",
                        Alert.AlertType.ERROR).show();
            }
        } else {
            MessageBox.getBox("Error", "Please complete all fields before checkout!!!", Alert.AlertType.ERROR).show();
        }

    }

    public String getLocalDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return currentDate.format(formatter);
    }

    public void getCusInfo() throws SQLException {
        try {
            int cusID = Integer.parseInt(txtcusID.getText());
            if (CustomerService.getCustomerByID(cusID) != null) {
                Date cusDate = CustomerService.getCustomerByID(cusID).getCusBirthOfDate();
                String cusName = CustomerService.getCustomerByID(cusID).getCusName();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String cusDateString = dateFormat.format(cusDate);
                lbCusDate.setText(cusDateString);
                lbCusName.setText(cusName);
                checkVoucher();
                checkCus = true;
            } else {
                MessageBox.getBox("Warning", "Please input correct ID Customer", Alert.AlertType.WARNING).show();
                txtcusID.clear();
                lbCusDate.setText("");
                lbCusName.setText("");
                checkCus = false;
            }
        } catch (NumberFormatException| SQLException ex ) {
            MessageBox.getBox("Warning", "Please input correct ID Customer", Alert.AlertType.WARNING).show();
            lbCusDate.setText("");
            lbCusName.setText("");
            checkCus = false;
        }

    }
    
    public void checkVoucher() {
        String[] cusDate = this.lbCusDate.getText().split("-");
        String[] date = this.lbDate.getText().split("-");
        float totalAmount = getTotalAmount();
        if (!lbCusDate.getText().isEmpty() && !lbDate.getText().isEmpty()) {
            if (cusDate[0].compareTo(date[0]) == cusDate[1].compareTo(date[1]) && totalAmount >= 1000000) {
                voucher = 0.9f;

            } else {
                voucher = 1f;
            }
        }

    }
  
    public void reset() {
        txtcusID.clear();
        lbCusDate.setText("");
        lbCusName.setText("");
        this.cbProduct.getSelectionModel().selectFirst();
        txtCustomerMoney.clear();
        txtTotalAmount.clear();
        tbProduct.getItems().clear();
    }
}
