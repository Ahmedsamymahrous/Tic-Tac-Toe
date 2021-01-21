/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import dbconnection.SignUpDB;
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
 *
 * @author Islam
 */
public class SignUpController implements Initializable {
    
    private Label label;
    @FXML
    private Label labelName;
    @FXML
    private Label labelPassword;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Label labelRePassword;
    @FXML
    private PasswordField tfRepassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private JFXButton btnSignIn;
    
    
    
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validate(String emailStr) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();
    }
    @FXML
    private void signUpAction(ActionEvent event) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        if(!validate(tfEmail.getText()))
        {
            //email is not valid
            alertError("Error","Invalid E-mail !!");
            clearNodes();
        }else{ 
            if(tfRepassword.getText().equals(tfPassword.getText()))
            {
                Player p = new Player(tfName.getText(),tfEmail.getText(),tfPassword.getText());
                SignUpDB db = new SignUpDB();
                db.Connect();
                System.out.println("isEz"+db.isExist(p));
                if(!db.isExist(p)){
                    //insert successfull
                    System.out.println("inside isExist");
                    boolean bol = db.newPlayer(p);
                    //p = db.getPlayerData();
                    
                    System.out.println("after new player " + bol);
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Account created successfully");
                    alert.showAndWait();
                    
                   /* Parent root = FXMLLoader.load(getClass().getResource("/fxmls/PlayingMode.fxml"));
                    Scene scene = new Scene(root);

                    //This line gets the Stage information
                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

                    window.setScene(scene);
                    window.show();*/
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
                }else
                {
                    // player is already exist
                    alertError("Error","This email is already exist!!");
                    clearNodes();
                }
                
            }else
            {
                // passwords don't match
                alertError("Error","Passwords don't match !!");
                clearNodes();
                
            }
        }
    }
    
    @FXML
    private void signInAction(ActionEvent event) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Login.fxml"));
        Scene scene = new Scene(root);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    private void alertError(String title , String msg){
        Alert alert ;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        //notSavedAlert.showAndWait();
        alert.showAndWait();
    }
    
    private void clearNodes()
    {
        tfName.clear();
        tfEmail.clear();
        tfPassword.clear();
        tfRepassword.clear();
    }
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
