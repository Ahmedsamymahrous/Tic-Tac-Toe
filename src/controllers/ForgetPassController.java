/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dbconnection.LoginDB;
import dbconnection.Player;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * FXML Controller class
 *
 * @author Aya Abdulsamie
 */
public class ForgetPassController implements Initializable {

    @FXML
    private Label labelName;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button resetbtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    public void resetButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException,IllegalAccessException,InstantiationException
    {
         if(validateEmailPattern(tfEmail.getText()))
        {
                   Player p = new Player(tfEmail.getText());
                  LoginDB db = new LoginDB();
                  db.Connect();
                  System.out.println("isEz"+db.isExist(p,false));
                  if(db.isExist(p,false))
                  {
                             sendEmail(event,p.getEmail());
                  }
                  else
                  {
                      alertError("Invalid","Email doesn't exist");
                       clearNodes();
                  }
        }
         else
        {
                alertError("Invalid","Invalid email pattern");
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
    private void clearNodes()
    {
        tfEmail.clear();
    }
 private void alertError(String title , String msg)
 {
       Alert alert ;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void sendEmail(ActionEvent event,String playerEmail) throws IOException
    {
        String userName = new String("tictactoegame660"); //change to sender username 
            String password = new String("intake41");  //change to sender pass
            String sendingHost = "smtp.gmail.com";     
            int sendingPort = 465;
            String from = new String("tictactoegame660@gmail.com");       //change to sender email
            String to = new String(playerEmail);           //change to receiver email
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
             
             String s = new String();
               s += alert.showAndWait().get().getText();
               if(s.equals("OK"))
                {
                    Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Login.fxml"));
                    Scene scene = new Scene(root);

                    Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();

                    window.setScene(scene);
                    window.show();
                 
                }
 
        } catch (MessagingException e) {
 
            e.printStackTrace();
        }
    }
    
}
