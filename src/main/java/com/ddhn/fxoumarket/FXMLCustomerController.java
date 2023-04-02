/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.utils.MessageBox;
import com.ddhn.pojo.Customer;
import com.ddhn.services.CustomerService;
import com.ddhn.services.EmployeeService;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javafx.util.StringConverter;
/**
 *
 * @author Nome
 */
public class FXMLCustomerController implements Initializable{
    @FXML
    private TableView<Customer> customerTable;
    
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField txtPersonalID;
    @FXML
    private TableColumn<Customer, Integer> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> personalIDCol;
    @FXML
    private TableColumn<Customer, Date> dateOfBirthCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    
    int index = -1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            loadTableView();
            
        } catch (Exception ex) {
            Logger.getLogger(FXMLEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dpDate.setConverter(converter);
    }
    
    private void loadTableView() throws SQLException
    {
        TableColumn colID = new TableColumn("ID");
        colID.setCellValueFactory(new PropertyValueFactory("CusId"));
        colID.setPrefWidth(50);

        nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("CusName"));
        nameCol.setPrefWidth(200);

        phoneCol = new TableColumn("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory("CusPhone"));
        phoneCol.setPrefWidth(150);
        
        dateOfBirthCol = new TableColumn("Birth of date");
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("CusBirthOfDate"));
        dateOfBirthCol.setPrefWidth(100);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        dateOfBirthCol.setCellFactory(column -> new TableCell<Customer, Date>() {
            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (date == null || empty) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(date));
                }
            }
        });
       
        personalIDCol = new TableColumn("Personal ID");
        personalIDCol.setCellValueFactory(new PropertyValueFactory("CusPersonalID"));
        personalIDCol.setPrefWidth(150);

        this.customerTable.getColumns().addAll(colID, nameCol, personalIDCol, dateOfBirthCol, phoneCol);
        List<Customer> Cus = CustomerService.getCustomers();
        this.customerTable.getItems().clear();
        this.customerTable.setItems(FXCollections.observableList(Cus));   
    }
    
    
    public void addCustomer(ActionEvent e) throws SQLException{
        
        if (dpDate.getValue() != null && !txtPhone.getText().trim().isEmpty() && !txtPersonalID.getText().trim().isEmpty() && !txtName.getText().trim().isEmpty()) 
        {
            try(Connection conn = JdbcUtils.getConn()){
                String name, phone, personalID;
                Date date;
                
                name = txtName.getText().trim();
                phone = txtPhone.getText().trim();
                date = Date.valueOf(dpDate.getValue());
                personalID = txtPersonalID.getText().trim();
                Optional<ButtonType> rs = MessageBox.getBox("Confirm", "Are you sure to insert this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if(rs.get() == ButtonType.OK)
                {
                    CustomerService.addCustomer(new Customer(name, phone, date, personalID));
                    MessageBox.getBox("Success", "Inserted successfully!!!", Alert.AlertType.INFORMATION).show();
                    loadTableView();
                    txtName.clear();
                    txtPhone.clear();
                    txtPersonalID.clear();
                    dpDate.setValue(null);
                }
                
            }
        } else
        {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();    
//            MessageBox.getBox("Error", String.valueOf(798*(10*1.0/100)), Alert.AlertType.ERROR).show();
        }
    }
    @FXML
    public void getSelected(MouseEvent e)
    {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        index = customerTable.getSelectionModel().getSelectedIndex();
        if(index <= -1) {
        } else {
            txtName.setText(nameCol.getCellData(index));
            txtPhone.setText(phoneCol.getCellData(index));
            txtPersonalID.setText(personalIDCol.getCellData(index));
            LocalDate localDate = dateOfBirthCol.getCellData(index).toLocalDate();
            String formattedDate = localDate.format(outputFormatter);
            dpDate.setValue(LocalDate.parse(formattedDate, outputFormatter));
        }
    }
    public void updateCustomer(ActionEvent e) throws SQLException{
        if (dpDate.getValue() != null && !txtPhone.getText().trim().isEmpty() && !txtPersonalID.getText().trim().isEmpty() && !txtName.getText().trim().isEmpty()) 
        {
            try(Connection conn = JdbcUtils.getConn()){
                String name, phone, personalID;
                Date date;
                int id;
                
                id = Integer.parseInt(String.valueOf(customerTable.getItems().get(index).getCusId()));
                name = txtName.getText().trim();
                phone = txtPhone.getText().trim();
                date = Date.valueOf(dpDate.getValue());
                personalID = txtPersonalID.getText().trim();
                Optional<ButtonType> rs = MessageBox.getBox("Confirm", "Are you sure to insert this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if(rs.get() == ButtonType.OK)
                {
                    CustomerService.updateCustomer(new Customer(id, name, phone, date, personalID));
                    MessageBox.getBox("Success", "Inserted successfully!!!", Alert.AlertType.INFORMATION).show();
                    loadTableView();
                    txtName.clear();
                    txtPhone.clear();
                    txtPersonalID.clear();
                    dpDate.setValue(null);
                }
                
            }
        } else
        {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();            
        }
    }
    public void deleteCustomer(ActionEvent e) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            int id;
            id = Integer.parseInt(String.valueOf(customerTable.getItems().get(index).getCusId()));

            Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to delete this field?",
                    Alert.AlertType.CONFIRMATION).showAndWait();
            if (result.get() == ButtonType.OK) {
                CustomerService.deleteCustomer(id);
                MessageBox.getBox("Success", "Deleted successfully!!!", Alert.AlertType.INFORMATION).show();
                loadTableView();
                txtName.clear();
                txtPhone.clear();
                txtPersonalID.clear();
                dpDate.setValue(null);

            } else if (result.get() == ButtonType.CANCEL) {

            }

        }
    }
    
}
