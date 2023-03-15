/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddhn.fxoumarket;

import com.ddhn.services.LoginService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author truon
 */
public class FXMLMainController implements Initializable {
    @FXML private Label lbBranchName;
    @FXML private Label lbUsername;
    @FXML private VBox vbScreen;

    @FXML private Button btnProduct;
    @FXML private Button btnPurchase;
    @FXML private HBox branchScreen;
    @FXML private HBox employeeScreen;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void showInformation(String username, String branch) {
        lbUsername.setText(username);
        lbBranchName.setText(branch);
    }
    
    public void logout (ActionEvent e) throws IOException{
        ((Node)e.getSource()).getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void exit(ActionEvent e) {
        Platform.exit();
    }

    public void showEmployee(ActionEvent e) {
        employeeScreen.setVisible(true);
        employeeScreen.setManaged(true);
        branchScreen.setVisible(false);
        branchScreen.setManaged(false);      
    }

    public void showBranch(ActionEvent e) {
        branchScreen.setVisible(true);
        branchScreen.setManaged(true);      
        employeeScreen.setVisible(false);
        employeeScreen.setManaged(false);
    }
    
}
    
