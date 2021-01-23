/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dbconnection.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohab
 */
public class ServerHomeController implements Initializable {
    @FXML
    private Button start;
    @FXML
    private TextArea taLog;
    @FXML
    private Button stop;
    @FXML
    private Button listUsers;
    @FXML
    private TableView<Player> table = new TableView();
    @FXML
    private TableColumn<Player, String> nameCol;
    @FXML
    private TableColumn<Player, String> emailCol ;
    @FXML
    private TableColumn<Player, Integer> scoreCol ;
    @FXML
    private TableColumn<Player, ImageView> statusCol ;
    @FXML
    private TableColumn<Player, ImageView> avatarCol ;
    
    private ServerSocket serverSocket;
    
    private boolean ServerOn = false;
    private int sessionNo = 1;
    Thread main;
    boolean tableOn = false;
    DBMS db = new DBMS();
    private DataInputStream dis;
    private PrintStream ps;
    private Player p;
     private ObjectMapper mapper;
    //private Map<String, Player> elements;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //set up columns
        nameCol.setCellValueFactory(new PropertyValueFactory<Player,String>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Player,String>("email"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<Player,Integer>("main_score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Player,ImageView>("stat"));
        avatarCol.setCellValueFactory(new PropertyValueFactory<Player,ImageView>("avat"));
    }    
    
    public void ViewUsersAction(ActionEvent event) throws IOException
    {
        if(tableOn == true)
        {
            tableOn = false;
        }else{
            tableOn = true;
        }
            table.setVisible(tableOn);
        try {
            table.setItems(db.ViewForServer());
        } catch (InstantiationException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void StartServer(ActionEvent event)
    {
        mapper = new ObjectMapper();
        ServerOn = true;
        start.setDisable(ServerOn);
        if(main == null)
        {
        main = new Thread( new Runnable() {
            @Override
            public void run() {
                try 
                {
                    // Create a server socket
                    serverSocket = new ServerSocket(5005);
                    
                    //start.disableProperty();
                    Platform.runLater(() -> taLog.appendText(new Date() + ": Server started at socket 5005\n")); 
                    
                    // Ready to create a session for every two players
                    while (ServerOn)
                    {
                        Platform.runLater(() -> taLog.appendText(new Date() + ": Wait for players to join session " + sessionNo + '\n'));
                        
                               Socket s = serverSocket.accept();
                                dis = new DataInputStream(s.getInputStream());
                                ps = new PrintStream(s.getOutputStream());
                                String msg = dis.readLine();
                                 System.out.println(msg);
                           
                            /** Convert from json string to map  **/ 
                             Map<String, Player> recMsg = mapper.readValue(msg,new TypeReference<Map<String, Player>>() {});
                             System.out.println(recMsg.values().toArray()[0]);
                              p = (Player) recMsg.values().toArray()[0];
                           
                        Platform.runLater(() ->
                        {
                            taLog.appendText(new Date() + "Socket Number  "+ s +" joind and session number : "+ sessionNo + '\n');
                            taLog.appendText("Player 1's IP address" + s.getInetAddress().getHostAddress() + '\n');
                        });
                        /**check from which scene the request came from**/
                        if(recMsg.keySet().toArray()[0].equals("login"))
                        {
                                        signInAction();
                         }
                     
                    }
                    serverSocket.close();
                }
                catch(IOException ex) 
                {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }            
        });
        
        main.start();
        }else{
        main.resume();
    }
    }
    public void StopServer(ActionEvent event) 
    {
        ServerOn = false;
        start.setDisable(ServerOn);
        main.stop();
        
    }
    
      public void signUpAction() throws ClassNotFoundException
    {
        try {
            // use db to store it
            SignUpDB db = new SignUpDB();
            db.Connect();
            if (!db.isExist(p)) {
                //insert successfull
                System.out.println("inside isExist");
                boolean bol = db.newPlayer(p);
                //p = db.getPlayerData();

                System.out.println("after new player " + bol);
                
                if(bol)
                    ps.println("true");
                else
                    ps.println("false");
            } else {
                // player is already exist
                ps.println("false");
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ServerHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void signInAction() throws ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException, IOException 
    {
            //ObjectMapper mapper = new ObjectMapper();
            LoginDB db = new LoginDB();
            Map<String, Player> replyMsg = new HashMap();
            db.Connect();
            if(db.isExist(p,true))
            {
                     p = db.getPlayerData();
                    replyMsg.put("true", p);
                    System.out.println("Found");
             } else {
                // player doesn't exist !
                replyMsg.put("false", p);
            }
                String json = mapper.writeValueAsString(replyMsg);
                //System.out.println(json);
                //System.out.println("inside sign in");
                ps.println(json);
    }
}