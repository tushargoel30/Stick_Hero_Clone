package com.example.mygame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

public class PauseScreen implements Serializable {
    private Main main;

    @FXML
    private Button exitutton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button saveGameButton;

    @FXML
    void exit(MouseEvent event) {

    }

    @FXML
    void resumeGame(MouseEvent event) {
        main.showGameScreen();
    }

    @FXML
    void saveGame(MouseEvent event) {

    }

    public void setMainApp(Main main) {
        this.main=main;
    }
}
