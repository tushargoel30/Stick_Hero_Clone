package com.example.mygame;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class GameState implements Serializable {
    static final long serialVersionUID = 20L;
    private int score;
    private int cherryCount;
    
    public GameState(int score,int cherryCount){
    this.score = score;
    this.cherryCount= cherryCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCherryCount() {
        return cherryCount;
    }

    public void setCherryCount(int cherryCount) {
        this.cherryCount = cherryCount;
    }

    public static GameState loadGameState() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("SaveGame.txt"))) {
            return (GameState) inputStream.readObject();

        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
