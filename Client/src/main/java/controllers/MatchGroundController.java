/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dbconnection.ListUsers;
import dbconnection.Player;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import dbconnection.PlayerConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
    private Label textLoad;
    @FXML
    private Label score;
    @FXML
    private ImageView icon;
    @FXML
    private ImageView load;
    @FXML
    private Button backBtn;
    @FXML
    private Button matchBtn;
    @FXML
    private Button chatBtn;
    @FXML
    private TableView<ListUsers> table ;
    @FXML
    private TableColumn<ListUsers, String> nameCol ;
    @FXML
    private TableColumn<ListUsers, Integer> scoreCol ;
    @FXML
    private TableColumn<ListUsers, ImageView> statusCol ;
    @FXML
    private TableColumn<ListUsers, ImageView> avatarCol ;
    @FXML
    private BorderPane pane1;
    private PlayerConnection connectPlayer;
    private Player player;
    ArrayList<Player> list;
    public void init(Player player,PlayerConnection connectPlayer)
    {
        this.connectPlayer = connectPlayer;
        this.player = player;
        RenderData();
    }
    
    @FXML
    public void playMatch(ActionEvent event) throws IOException
    {
        //get selected player from list
        ListUsers selectedUser = table.getSelectionModel().getSelectedItem();
        System.out.println("play with : "+selectedUser.getName());
        //making user wait

        load.setVisible(true);
        table.setOpacity(.3);
        matchBtn.setDisable(true);
        textLoad.setVisible(true);
        backBtn.setDisable(true);
        //sending selected user to play with.
        connectPlayer.serialaize("play",selectedUser.mapToPlayer());


        //in case player accepted
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmls/PlayWithFriend.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //access the controller and call a method
        PlayWithFriendController controller = loader.getController();
        controller.init(player,connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        //window.setScene(scene);
        //    window.show();
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
       controller.getData(player,true,connectPlayer);
        
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
        connectPlayer.serialaize("logout",player);
        Map<String, Player> elements = connectPlayer.deserialize();
        System.out.println(elements.values().toArray()[0]);

        connectPlayer.closeConnection();
        System.out.println("closed");
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
        controller.init(player,connectPlayer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
    //here we view users on table
    public ObservableList<ListUsers> ViewTable(ArrayList<Player> allPlayers){
        ObservableList<ListUsers> Data = FXCollections.observableArrayList();
        ListUsers user;
        for(Player p : allPlayers) {
            user = new ListUsers(p.getPlayerID(),p.getName(), p.getEmail(),p.getPassword(),p.getMain_score(), p.getStatus(),p.getAvatar());
            if(p.getStatus() == 1)
            {
                user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/on.png"))));
            }else{
                user.setStat(new ImageView(new Image(getClass().getResourceAsStream("/icons/off.png"))));
            }
            user.setAvat(new ImageView(new Image(getClass().getResourceAsStream("/icons/"+p.getAvatar()))));
            Data.add(user);
        }
        return Data;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set up columns
        nameCol.setCellValueFactory(new PropertyValueFactory<ListUsers,String>("name"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<ListUsers,Integer>("main_score"));
        statusCol.setCellValueFactory(new PropertyValueFactory<ListUsers,ImageView>("stat"));
        avatarCol.setCellValueFactory(new PropertyValueFactory<ListUsers,ImageView>("avat"));


    }
    public void RenderData(){
        //requesting all users from server
        connectPlayer.serialaize("list",player);
        //getting all Users from Server
        Map<String, ArrayList<Player>> elements =  connectPlayer.deserializeList();
        list = (ArrayList<Player>) elements.values().toArray()[0];
        //here you will send the function List of players
        table.setItems(ViewTable(list));
        //Render Player Data on Scene
        icon.setImage(new Image(getClass().getResourceAsStream("/icons/"+player.getAvatar())));
        score.setText(Integer.toString(player.getMain_score()));
        name.setText(player.getName());
    }
}
