/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dbconnection.LoginDB;
import dbconnection.Player;
import dbconnection.SignUpDB;
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
    private void signInButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        //Vlidate entered email pattern and password
        if(validateEmailPattern(tfEmail.getText()))
        {
              if(!tfPassword.getText().trim().isEmpty())
              {
                   Player p = new Player(tfEmail.getText(),tfPassword.getText());
                  LoginDB db = new LoginDB();
                  db.Connect();
                  
                  if(db.isExist(p,true))
                  {
                         moveToPlayingModeOptions(event);
                  }
                  else
                  {
                      alertError("Invalid","Wrong email or password");
                       clearNodes();
                  }
              }
              else
              {
                  alertError("Invalid password","Password field can't be empty!");
                   clearNodes();
              }
        }
        else
        {
                alertError("Invalid email","Invalid email pattern");
                 clearNodes();
        }
    }
    private boolean validateEmailPattern(String email)
    {
             String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            
            return matcher.matches();
     
    }
   @FXML
    private void moveToPlayingModeOptions(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Scene scene = new Scene(root);

        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
   @FXML
    private void forgetPasswordButtonPushed(ActionEvent event)  throws IOException
    {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/ForgetPass.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }  
  private void clearNodes()
    {
        tfEmail.clear();
        tfPassword.clear();
    }
 private void alertError(String title , String msg)
 {
       Alert alert ;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
