/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
import com.ddhn.pojo.Employee;
import com.ddhn.services.BranchService;
import com.ddhn.services.EmployeeService;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLEmployeeController implements Initializable {

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> idCol;
    @FXML
    private TableColumn<Employee, String> nameCol;
    @FXML
    private TableColumn<Employee, String> phoneCol;
    @FXML
    private TableColumn<Employee, String> usernameCol;
    @FXML
    private TableColumn<Employee, String> passwordCol;
    @FXML
    private TableColumn<Employee, Integer> branchIdCol;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ComboBox<Branch> cbBranch;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    int index = -1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renewTable();
        try {
            loadBranch();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadBranch() throws SQLException {
        List<Branch> branchList = BranchService.getBranchs();
        cbBranch.setItems(FXCollections.observableList(branchList));
    }

    public void renewTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("password"));
        branchIdCol.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("branch_id"));

        ObservableList<Employee> employeeList = null;
        try {
            employeeList = FXCollections.observableArrayList(EmployeeService.getEmployees());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBranchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        employeeTable.setItems(employeeList);
    }

    public void addEmployee(ActionEvent e) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()
                || txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, phone, username, password;
                int branchId;

                name = txtName.getText();
                phone = txtPhone.getText();
                username = txtUsername.getText();
                password = txtPassword.getText();
                branchId = cbBranch.getSelectionModel().getSelectedItem().getId();
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to insert this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    EmployeeService.addEmployee(new Employee(name, phone, username, password, branchId));
                    MessageBox.getBox("Success", "Inserted successfully!!!", Alert.AlertType.INFORMATION).show();
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

    }

    public void updateEmployee(ActionEvent e) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()
                || txtUsername.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before update!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, phone, username, password;
                int id, branchId;

                name = txtName.getText();
                phone = txtPhone.getText();
                username = txtUsername.getText();
                password = txtPassword.getText();
                id = Integer.parseInt(String.valueOf(employeeTable.getItems().get(index).getId()));
                branchId = cbBranch.getSelectionModel().getSelectedItem().getId();
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to update this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    EmployeeService.updateEmployee(new Employee(id, name, phone, username, password, branchId));
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
}
