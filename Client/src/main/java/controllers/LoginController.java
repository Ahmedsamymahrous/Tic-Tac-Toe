/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import controllers.PlayingModeController;
import dbconnection.Player;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Socket s;
    private DataInputStream dis;
    private PrintStream ps;
    private ObjectMapper mapper;
    private Player p;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //new LoginController();
    }    
   
    public void connectPlayer() throws IOException
    {
          s = new Socket("127.0.0.1", 5005);
          dis = new DataInputStream(s.getInputStream());
           ps = new PrintStream(s.getOutputStream());
           System.out.println("Sign in connected");
    }
    @FXML
    private void signInButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        //Vlidate entered email pattern and password
        if(validateEmailPattern(tfEmail.getText()))
        {
              if(!tfPassword.getText().trim().isEmpty())
              {
                             p = new Player(tfEmail.getText(), tfPassword.getText());
                                 connectPlayer();
 
                            Map<String, Player> sentMsg = new HashMap();
                            sentMsg.put("login", p);
                            
                            ObjectMapper objectMapper = new ObjectMapper();

                            try {
                                String json = objectMapper.writeValueAsString(sentMsg);
                                System.out.println(json);
                                ps.println(json);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            
                         String replyMsg = dis.readLine();
                          Map<String, Player> recMsg = objectMapper.readValue(replyMsg,new TypeReference<Map<String, Player>>() {});
                        System.out.println(recMsg.values().toArray()[0]);

                        if(recMsg.keySet().toArray()[0].equals("true"))
                        {
                            System.out.println("it works!");
                             p = (Player) recMsg.values().toArray()[0];
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
        closeConnection();
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
        public void closeConnection() {
        try {
            dis.close();
            ps.close();
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
