/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
