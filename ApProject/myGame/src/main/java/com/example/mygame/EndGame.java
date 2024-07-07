package com.example.mygame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class EndGame {
    private Main main;


    @FXML
    private Label score;

    @FXML
    private Label highScore;

    @FXML
    private Button homeButton;

    @FXML
    private Button retryButton;

    @FXML
    private Button reviveButton;
    private int cherryCount;

    public void setCherryCount(int cherryCount) {
        this.cherryCount = cherryCount;
    }

    @FXML
    public void revivePlayer(ActionEvent event) {
        if(cherryCount>=3)
        {
            main.showGameScreen(Integer.parseInt(score.getText()),cherryCount-3);
        }
    }

    public void setMainApp(Main main) {
        this.main=main;
    }

    public void updateValue(int Score) {
        if(Score>=main.getHighScore())
        {
            String audioPath = "/Audio/GreatScore.mp3";
            Media sound = new Media(getClass().getResource(audioPath).toString());
            MediaPlayer mp= new MediaPlayer(sound);
            mp.play();
            main.setHighScore(Score);
        }
        score.setText(String.valueOf(Score));
        highScore.setText(String.valueOf(main.getHighScore()));
        homeButton.setOnAction(event->
        {
            main.showMainMenu();
        });
        retryButton.setOnAction(event->
        {
            main.showGameScreen();
        });
    }
}
