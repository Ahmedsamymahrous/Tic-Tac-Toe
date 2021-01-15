/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import tictactoe.*;
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

    public void PlayMatch(ActionEvent event) throws IOException
    {
        Parent play = FXMLLoader.load(getClass().getResource("/fxmls/PlayWithFriend.fxml"));
        Scene playMatch = new Scene(play);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(playMatch);
        window.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
