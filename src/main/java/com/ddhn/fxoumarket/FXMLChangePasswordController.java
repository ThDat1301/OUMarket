/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.pojo.Employee;
import com.ddhn.services.EmployeeService;
import com.ddhn.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLChangePasswordController implements Initializable {

    @FXML
    PasswordField txtOldPass;
    @FXML
    PasswordField txtNewPass;
    @FXML
    PasswordField txtConfirm;
    @FXML
    VBox root;

    private int empId;

    public void setEmplId(int id) {
        empId = id;
    }

    public int getEmplId() {
        return empId;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void changePassword(ActionEvent e) throws SQLException {
        Employee emp = EmployeeService.getEmpById(getEmplId());
        String oldPassword = DigestUtils.md5Hex(txtOldPass.getText());
        if (txtOldPass.getText().isEmpty() || txtNewPass.getText().isEmpty() || txtConfirm.getText().isEmpty()) {
            MessageBox.getBox("Error", "Please complete all fields before change password!!!", Alert.AlertType.ERROR).show();
        } else {
            if (oldPassword.equals(emp.getPassword())) {
                if (txtNewPass.getText().equals(txtConfirm.getText())) {
                    Optional<ButtonType> result = MessageBox.getBox("Confirm", "Are you sure to change password?",
                            Alert.AlertType.CONFIRMATION).showAndWait();
                    if (result.get() == ButtonType.OK) {
                        emp.setPassword(txtNewPass.getText());
                        EmployeeService.updateEmployee(emp);
                        MessageBox.getBox("Success", "Change Password Successfully", Alert.AlertType.INFORMATION).show();
                        Stage stage = (Stage) root.getScene().getWindow();
                        stage.close();
                    }
                    
                } else {
                    MessageBox.getBox("Warning", "Two passwords are not match. Please check again", Alert.AlertType.WARNING).show();
                }
            } else {
                MessageBox.getBox("Warning", "Old password is wrong. Please check again", Alert.AlertType.WARNING).show();
            }
        }

    }

}
