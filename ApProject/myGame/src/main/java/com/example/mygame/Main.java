package com.example.mygame;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    MediaPlayer mediaPlayer;
    private int highScore=0;
    private int cherryCount=0;


    public int getcherryCount(){
        return cherryCount;}
    public void setCherryCount(int cherryCount){
        this.cherryCount = cherryCount;
        }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        showMainMenu();
        String audioPath = "/Audio/bgMusic.mp3";
        Media sound = new Media(getClass().getResource(audioPath).toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.play();
    }
    public static void main(String[] args) {
        launch();
    }

    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            MainMenu controller = loader.getController();
            controller.setMainApp(this);
            controller.setHighScore(highScore);
            Scene scene = new Scene(root,900, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Stick Hero");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showGameScreen() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            Parent root = loader.load();

            GameScreen controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root,900, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Stick Hero");
            primaryStage.setResizable(false);
            primaryStage.show();
            controller.startGameLoop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Scene getScene()
    {
        return primaryStage.getScene();
    }




    public void showEndGame(int score, int i) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("endGame.fxml"));
            Parent root = loader.load();

            EndGame controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root,900, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Stick Hero");
            primaryStage.setResizable(false);
            primaryStage.show();
            controller.updateValue(score);
            controller.setCherryCount(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        highScore=score;
    }

    public void showGameScreen(int score, int cherry) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            Parent root = loader.load();

            GameScreen controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(root,900, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Stick Hero");
            primaryStage.setResizable(false);
            primaryStage.show();
            controller.startGameLoop();
            controller.setScore(score);
            controller.setCherryCount(cherry);
            controller.setValue(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}