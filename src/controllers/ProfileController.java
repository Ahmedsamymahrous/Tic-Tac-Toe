/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

/**
 *
 * @author Islam
 */
public class ProfileController implements Initializable {
    
    private Label label;
    @FXML
    private JFXTextField tfName;
    @FXML
    private JFXTextField tfEmail;
    @FXML
    private JFXTextField tfPassword;
    @FXML
    private Button btnSubmit;
    @FXML
    private JFXButton btnBackHome;
    @FXML
    private ImageView imgProfile;
     @FXML
    private void logoutButtonPushed(ActionEvent event)  throws IOException
    {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }
     @FXML
    private void backButtonPushed(ActionEvent event)  throws IOException
    {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/MatchGround.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }
     @FXML
    private void saveButtonPushed(ActionEvent event)  throws IOException
    {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Data updated successfully");
                alert.showAndWait();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      //btnSubmit.shortcut();
    }    
    
}
