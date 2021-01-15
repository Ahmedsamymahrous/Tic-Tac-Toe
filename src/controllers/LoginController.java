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
        if(validateEmailPattern(tfEmail.getText()))
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
    public boolean validateEmailPattern(String email)
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
           String userName = new String("username"); //change to sender username 
            String password = new String("pass");  //change to sender pass
            String sendingHost = "smtp.gmail.com";     
            int sendingPort = 465;
            String from = new String("email");       //change to sender email
            String to = new String("email");           //change to receiver email
            String subject = new String("Reset password");
            String text = new String("12345 this is a temp password");
            String receivingHost;
            
        Properties props = new Properties();
 
        props.put("mail.smtp.host", sendingHost);
        props.put("mail.smtp.port", String.valueOf(sendingPort));
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", password);
 
        props.put("mail.smtp.auth", "true");
 
         Session session1 = Session.getDefaultInstance(props);
 
         Message simpleMessage = new MimeMessage(session1);
 
        //MIME stands for Multipurpose Internet Mail Extensions
 
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
 
        try {
 
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
 
        } catch (AddressException e) {
 
            e.printStackTrace();
 
        }
 
        try {
 
            simpleMessage.setFrom(fromAddress);
 
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
 
            simpleMessage.setSubject(subject);
 
            simpleMessage.setText(text);
 
            Transport transport = session1.getTransport("smtps");
 
            transport.connect (sendingHost,sendingPort, userName, password);
 
            transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());
 
            transport.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setContentText("An email was sent to u");
             alert.showAndWait();
 
        } catch (MessagingException e) {
 
            e.printStackTrace();
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
