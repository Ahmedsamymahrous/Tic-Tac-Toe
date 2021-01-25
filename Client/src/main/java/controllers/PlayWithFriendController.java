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

import dbconnection.PlayerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mohab
 */
public class PlayWithFriendController implements Initializable {

    @FXML
    private ImageView player1Icone;
    @FXML
    private ImageView player2Icon;
    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private Hyperlink player1MainScore;
    @FXML
    private Hyperlink player2MainScore;
    @FXML
    private Label player1Score;
    @FXML
    private Label player2Score;
    @FXML
    private GridPane groundGrid;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn3;
    @FXML
    private Button btn6;
    @FXML
    private Button btn9;
    @FXML
    private Button chatBtn;
    @FXML
    private Button leaveBtn;
    @FXML
    private Pane chatRoom;
    @FXML
    private TextArea chatView;
    @FXML
    private TextField message;
    @FXML
    private Button sendBtn;
    private boolean chatOn = false;
    private PlayerConnection connectPlayer;
    private Player player;

    public void init(Player player,PlayerConnection connectPlayer)
    {
        this.connectPlayer = connectPlayer;
        this.player = player;
        player1Name.setText(player.getName());
        player1MainScore.setText(Integer.toString(player.getMain_score()));
        player1Score.setText("0");
        player2Score.setText("0");
    }
    
    @FXML
    private void leaveMatchButtonPushed(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/PlayingMode.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        //access the controller and call a method
        PlayingModeController controller = loader.getController();
        controller.init(player,connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    @FXML
    private void Chat(ActionEvent event) {
        if(chatOn == true)
        {   
            chatOn = false;
        }else if(chatOn == false)
        {
            chatOn = true;
        }
        chatRoom.setVisible(chatOn);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   
}
