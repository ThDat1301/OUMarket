/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.services.LoginService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLLoginController implements Initializable {
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    public void loginHandler(ActionEvent e) throws SQLException, IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
       
        if (LoginService.Login(username, password) == 1) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMain.fxml"));
            Parent root = loader.load();
            String branch = LoginService.getBranchByUsername(username);
            FXMLMainController mainController = loader.getController();
            mainController.showInformation(username, branch);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ((Node)e.getSource()).getScene().getWindow().hide();
        } 
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
