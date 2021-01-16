/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnection;

/**
 *
 * @author Aya Abdulsamie
 */
public class Player {
    private int playerID;
    private String name;
    private String email;
    private String password;
    private int main_score;
    private String status;
    private String avatar;

    public Player(int playerID, String name, String email, String password, int main_score, String status, String avatar) {
        this.playerID = playerID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.main_score = main_score;
        this.status = status;
        this.avatar = avatar;
    }
    
    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public Player(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Player(String email) {
        this.email = email;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMain_score() {
        return main_score;
    }

    public void setMain_score(int main_score) {
        this.main_score = main_score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    

}
