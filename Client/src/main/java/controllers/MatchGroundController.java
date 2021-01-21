/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dbconnection.Player;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohab
 */
public class MatchGroundController implements Initializable {

    @FXML
    private Label appName;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label name;
    @FXML
    private Label score;
    @FXML
    private ImageView icon;
    @FXML
    private Button backBtn;
    @FXML
    private Button matchBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private ListView<?> listView;
    private Player p;
    
    public void init(Player player)
    {
        p = player;
        name.setText(player.getName());
    }
    
@FXML
    public void playMatch(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmls/PlayWithFriend.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            //access the controller and call a method
            PlayWithFriendController controller = loader.getController();
            controller.init(p);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
    }
    @FXML
    public void profileButtonPushed(ActionEvent event) throws IOException
    {
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/Profile.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        //access the controller and call a method
        ProfileController controller = loader.getController();
       controller.getData(p,true);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    @FXML
    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/Home.fxml"));
        Scene scene = new Scene(root);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(scene);
        window.show();
    }
    @FXML
    public void backButtonPushed(ActionEvent event) throws IOException
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
