package edu.hitsz.DAO;

import java.io.Serializable;
import java.util.Date;

public class GameData implements Serializable {
    private String username;
    private int score;
    private Date date;

    public GameData(String username, int score, Date date){
        this.username = username;
        this.score = score;
        this.date = date;
    }

    public String getUsername(){
        return username;
    }

    public int getScore(){
        return score;
    }

    public Date getDate(){
        return date;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
