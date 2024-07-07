package com.example.mygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class MainMenu implements Serializable{
    private Main main;

    @FXML
    private Label highScore;

    @FXML
    private Button loadGameButton;

    @FXML
    private Button playButton;

    @FXML
    void loadGame(ActionEvent event) {
        GameState gameState= GameState.loadGameState();
        int score=gameState.getScore();
        int Cherry=gameState.getCherryCount();
        main.showGameScreen(score,Cherry);
    }

    @FXML
    void playGame(ActionEvent event) {
        main.showGameScreen();
    }

    public void setMainApp(Main main) {

        this.main=main;
    }


    public void setHighScore(int highScore) {
        this.highScore.setText(String.valueOf(highScore));
    }
}
