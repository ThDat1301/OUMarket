/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.conf.JdbcUtils;
import com.ddhn.pojo.Branch;
import com.ddhn.services.BranchService;
import com.ddhn.utils.MessageBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FXMLBranchController implements Initializable {

    @FXML
    private TableView<Branch> branchTable;
    @FXML
    private TableColumn<Branch, Integer> idCol;
    @FXML
    private TableColumn<Branch, String> nameCol;
    @FXML
    private TableColumn<Branch, String> addressCol;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;

    int index = -1;

    public void renewTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<Branch, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Branch, String>("address"));
        ObservableList<Branch> branchList = null;
        try {
            branchList = FXCollections.observableArrayList(BranchService.getBranchs());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBranchController.class.getName()).log(Level.SEVERE, null, ex);
        }
        branchTable.setItems(branchList);
    }

    public void addBranch(ActionEvent e) throws SQLException {
        boolean a = txtName.getText().trim().isEmpty();
        boolean b = txtAddress.getText().trim().isEmpty();
        if (txtName.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before insert!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, address;
                name = txtName.getText();
                address = txtAddress.getText();

                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to update this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    BranchService.addBranch(new Branch(name, address));
                    txtName.clear();
                    txtAddress.clear();
                    renewTable();
                    MessageBox.getBox("Success", "Updated successfully!!!", Alert.AlertType.INFORMATION).show();
                } else if (result.get() == ButtonType.CANCEL) {

                }
            }
        }

    }

    @FXML
    public void getSelected(MouseEvent e) {
        index = branchTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtName.setText(nameCol.getCellData(index));
        txtAddress.setText(addressCol.getCellData(index));
    }

    public void updateBranch(ActionEvent e) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before update!!!", Alert.AlertType.ERROR).show();

        } else {
            try (Connection conn = JdbcUtils.getConn()) {
                String name, address;
                int id;

                name = txtName.getText();
                address = txtAddress.getText();
                id = Integer.parseInt(String.valueOf(branchTable.getItems().get(index).getId()));
                Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to update this field?",
                        Alert.AlertType.CONFIRMATION).showAndWait();
                if (result.get() == ButtonType.OK) {
                    BranchService.updateBranch(new Branch(id, name, address));
                    MessageBox.getBox("Success", "Updated successfully!!!", Alert.AlertType.INFORMATION).show();
                    renewTable();
                } else if (result.get() == ButtonType.CANCEL) {

                }
            }
        }

    }

    public void deleteBranch(ActionEvent e) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            int id;
            id = Integer.parseInt(String.valueOf(branchTable.getItems().get(index).getId()));
            renewTable();
            Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to delete this field?",
                    Alert.AlertType.CONFIRMATION).showAndWait();
            if (result.get() == ButtonType.OK) {
                BranchService.deleteBranch(id);
                MessageBox.getBox("Success", "Deleted successfully!!!", Alert.AlertType.INFORMATION).show();
                txtName.clear();
                txtAddress.clear();
                renewTable();
            } else if (result.get() == ButtonType.CANCEL) {

            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        renewTable();
    }

}
