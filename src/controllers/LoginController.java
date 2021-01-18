/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import dbconnection.LoginDB;
import dbconnection.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aya Abdulsamie
 */
public class LoginController implements Initializable {

    @FXML
    private Label labelEmail;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button signinbtn;
    @FXML
    private JFXButton forgetpassbtn;
    @FXML
    private JFXButton backbtn;
    @FXML
    private JFXButton signupbtn;
    private Player p;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void signInButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        //Vlidate entered email pattern and password
        if(validateEmailPattern(tfEmail.getText()))
        {
              if(!tfPassword.getText().trim().isEmpty())
              {
                  p = new Player(tfEmail.getText(),tfPassword.getText());
                  LoginDB db = new LoginDB();
                  db.Connect();
                  
                  if(db.isExist(p,true))
                  {
                        p = db.getPlayerData();
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
   
    @FXML
    private void forgetPasswordButtonPushed(ActionEvent event) throws IOException
    {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/ForgetPass.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }

    @FXML
    private void backButtonPushed(ActionEvent event) throws IOException
        {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
            Scene scene = new Scene(root);

            Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }

    @FXML
    private void signUpButtonPushed(ActionEvent event) throws IOException
    {
            Parent root = FXMLLoader.load(getClass().getResource("/fxmls/SignUp.fxml"));
                    Scene scene = new Scene(root);

                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

                    window.setScene(scene);
                    window.show();
    }
    
     private void moveToPlayingModeOptions(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        PlayingModeController controller = loader.getController();
        controller.init(p);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
      private boolean validateEmailPattern(String email)
    {
             String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            
            return matcher.matches();
     
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

}
