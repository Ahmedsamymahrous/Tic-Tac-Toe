/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.*;
import java.net.URL;
import java.util.regex.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;   
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.ResourceBundle;
import java.security.*;
import javax.activation.*;  

/**
 * FXML Controller class
 *
 * @author Aya Abdulsamie
 */
public class LoginController implements Initializable {

    @FXML
    private Label labelName;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    public void signInButtonPushed(ActionEvent event) throws IOException
    {
        //Vlidate entered email pattern and password
        if(validateEmailPattern(tfEmail.getText().trim()))
        {
              if(!tfPassword.getText().trim().isEmpty())
                     moveToPlayingModeOptions(event);
              else
              {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                  alert.setTitle("Error");
                  alert.setContentText("Password field can't be empty!");
                  alert.showAndWait();
              }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText("Invalid email pattern");
                alert.showAndWait();
        }
    }
    @FXML
    private boolean validateEmailPattern(String email)
    {
             String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            
            return matcher.matches();
     
    }
   @FXML
    public void moveToPlayingModeOptions(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Scene scene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
   @FXML
    public void forgetPasswordButtonPushed(ActionEvent event)  throws IOException
    {
       
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setContentText("Enter ur email");
                //alert.setResult(null);
               String s = new String();
               s += alert.showAndWait().get().getText();
               if(s.equals("OK"))
                {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxmls/ForgetPass.fxml"));
                    Scene scene = new Scene(root);

                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

                    window.setScene(scene);
                    window.show();
                    
                }
             
    }  
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
