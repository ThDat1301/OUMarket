/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
import com.ddhn.pojo.Employee;
import com.ddhn.pojo.Product;
import com.ddhn.services.BranchService;
import com.ddhn.services.EmployeeService;
import com.ddhn.services.ProductService;
import com.ddhn.utils.Function;
import com.ddhn.utils.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLEmployeeController implements Initializable {

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> idCol;
    @FXML private TableColumn<Employee, String> nameCol;
    @FXML private TableColumn<Employee, String> phoneCol;
    @FXML private TableColumn<Employee, String> usernameCol;
    @FXML private TableColumn<Employee, String> passwordCol;
    @FXML private TableColumn<Employee, Integer> branchIdCol;
    @FXML private TableColumn<Employee, Integer> roleCol;
    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private ComboBox<Branch> cbBranch;
    @FXML private ComboBox<String> cbRole;
    @FXML private Button btnInsert;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
//    @FXML private TextField txtRole;
            

    int index = -1;
    
    private int empId;
    
    public void setEmplId(int id) {
        empId = id;
    }
    
    public int getEmplId() {
        return empId;
    }

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            renewTable();
            this.cbRole.getItems().addAll("admin", "user");

        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.txtPhone.textProperty().addListener(e -> {
            if(!Function.isNumber(txtPhone.getText()))
            {
                txtPhone.setText("1");
            }
        });
    }
    
    public void loadBranch() throws SQLException {

        List<Branch> list = BranchService.getBranchs();
        cbBranch.setItems(FXCollections.observableList(list));
    }

    public void renewTable() throws SQLException {
        this.employeeTable.getItems().clear();
        this.employeeTable.getColumns().clear();
        idCol = new TableColumn("ID");
        nameCol = new TableColumn("Name");
        phoneCol = new TableColumn("Phone");
        usernameCol = new TableColumn("Username");
        passwordCol = new TableColumn("Password");
        branchIdCol = new TableColumn("Branch ID");
        roleCol  = new TableColumn("Role");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<>("branch_id"));  
        
        Callback<TableColumn<Employee, Integer>, TableCell<Employee, Integer>> roleCellFactory = new Callback<TableColumn<Employee, Integer>, TableCell<Employee, Integer>>() {
        @Override
        public TableCell<Employee, Integer> call(TableColumn<Employee, Integer> param) {
            return new TableCell<Employee, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                    setText(null);
                } else {
                    setText(item == 0 ? "user" : "admin");
                        }
                    }
                };
            }
        };
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setCellFactory(roleCellFactory);

        
        this.employeeTable.getColumns().addAll(idCol,nameCol,phoneCol,usernameCol,passwordCol,branchIdCol, roleCol);        
        List<Employee> employee = EmployeeService.getEmployees();        
        this.employeeTable.setItems(FXCollections.observableArrayList(employee));
        
        List<Branch> branchList = BranchService.getBranchs();
        cbBranch.setItems(FXCollections.observableList(branchList));
        
    }
    @FXML
    public void getSelected(MouseEvent e) throws SQLException {
        index = employeeTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtName.setText(nameCol.getCellData(index));
        txtPhone.setText(phoneCol.getCellData(index));
        txtUsername.setText(usernameCol.getCellData(index));
        txtPassword.setText(passwordCol.getCellData(index));
        int branchId = Integer.parseInt(String.valueOf(employeeTable.getItems().get(index).getBranch_id()));
        cbBranch.setValue(BranchService.getBranchById(branchId));
        
//        cbRole.setValue(roleCol.getCellData(index));
        String role = roleCol.getCellData(index) == 1 ? "admin" : "user";
        cbRole.setValue(role);

    }

    public void updateEmployee(ActionEvent e) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()
                || txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before update!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, phone, username, password;
                int id, branchId, role;

                name = txtName.getText();
                phone = txtPhone.getText();
                username = txtUsername.getText();
                password = txtPassword.getText();
                id = Integer.parseInt(String.valueOf(employeeTable.getItems().get(index).getId()));
                branchId = cbBranch.getSelectionModel().getSelectedItem().getId();
                int num = 0;
                if(cbRole.getSelectionModel().getSelectedItem() == "admin"){
                    num = 1;
                }
                role = num;
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to update this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    EmployeeService.updateEmployee(new Employee(id, name, phone, username, password, branchId, role));
                    MessageBox.getBox("Success", "Updated successfully!!!", Alert.AlertType.INFORMATION).show();
                    renewTable();
                } else if (result.get() == ButtonType.CANCEL) {

                }

            }
        }
    }

    public void deleteEmployee(ActionEvent e) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            int id;
            id = Integer.parseInt(String.valueOf(employeeTable.getItems().get(index).getId()));

            Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to delete this field?",
                    Alert.AlertType.CONFIRMATION).showAndWait();
            if (result.get() == ButtonType.OK) {
                EmployeeService.deleteEmployee(id);
                MessageBox.getBox("Success", "Deleted successfully!!!", Alert.AlertType.INFORMATION).show();
                renewTable();
                txtName.clear();
                txtPhone.clear();
                txtUsername.clear();
                txtPassword.clear();
                cbBranch.getSelectionModel().selectFirst();
            } else if (result.get() == ButtonType.CANCEL) {

            }

        }
    }
    
    public void showChangePassword() throws IOException, SQLException {
        if (index == -1) {
            MessageBox.getBox("Error", "Please choose the employee!", Alert.AlertType.WARNING).show();
        }
        else {
            int id = Integer.parseInt(String.valueOf(employeeTable.getItems().get(index).getId()));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLChangePassword.fxml"));
            Parent root = fxmlLoader.load();
            
            FXMLChangePasswordController fxmlChangePasswordController = fxmlLoader.getController();
            fxmlChangePasswordController.setEmplId(id);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            
            renewTable();
        }

        
    }
    
    public void addEmployee(ActionEvent e) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()
                || txtUsername.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, phone, username, password;
                int branchId, num = 0, role;

                name = txtName.getText();
                phone = txtPhone.getText();
                username = txtUsername.getText();
                password = txtPassword.getText();
                branchId = cbBranch.getSelectionModel().getSelectedItem() != null ? cbBranch.getSelectionModel().getSelectedItem().getId(): 1;
                if(cbRole.getSelectionModel().getSelectedItem() == "admin"){
                    num = 1;
                }
                role = num;
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to insert new employee?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (EmployeeService.addEmployee(new Employee(name, phone, username, password, branchId, role))) {
                        MessageBox.getBox("Success", "Inserted successfully!!!", Alert.AlertType.INFORMATION).show();
                        renewTable();
                        txtName.clear();
                        txtPhone.clear();
                        txtUsername.clear();
                        txtPassword.clear();
                        cbBranch.getSelectionModel().selectFirst();
                    } else MessageBox.getBox("Error", "Something went wrong, please try again!", Alert.AlertType.ERROR).show();
                } else if (result.get() == ButtonType.CANCEL) {

                }
            }
        }

    }
}
