package com.example.mygame;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
//import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


import java.util.Random;

import static java.lang.Math.abs;


public class GameScreen {

    private Main main;
    private Player player;
    private Platform p1;
    private Platform p2;
    private Platform p3;
    private Stick stick;
    private Boolean stickCreated;
    private boolean isFlipped = false;
    private Obstacle1 obstacle1;


    @FXML
    private Label cherryCount;
    private int Score = 0;

    @FXML
    private Button pauseButton;

    @FXML
    private Label score;
    @FXML
    private AnchorPane gamePane;
    private boolean stopGame = false;
    private Cherries cherry;

    private boolean step1 = true;
    private boolean step2 = false;
    private boolean step3 = false;
    private boolean step4 = false;
    private boolean step5 = false;
    private boolean gameRunning = true;
    private boolean isPaused = false;
    private boolean keyReleased = false;

    private boolean isMoving = false;
    private boolean cherryEaten=false;
    private boolean step6;
    private boolean valve=false;

    public void setValue(int value)
    {
        this.Score=value;
    }

    private void initialize() {
        pauseButton.setOnAction(event -> handlePauseButton());
        // Creating a new character
        player = new Player();

        player.setLayoutX(player.getxPos());
        player.setLayoutY(player.getyPos());
        gamePane.getChildren().add(player);

//        Creating initial Platform
        p1 = new Platform(100);
        p1.setLayoutX(0);
        p1.setLayoutY(400);
        gamePane.getChildren().add(p1);
        addPlatform2();

    }

    private int getCherryCount()
    {
        return Integer.parseInt(cherryCount.getText());
    }
    private void handlePauseButton() {
        isPaused = true;
        // Create a small pane with options
        VBox optionsPane = new VBox(10);
        optionsPane.setStyle("-fx-background-color: lightgray; -fx-padding: 20px;");

        Button resumeButton = new Button("Resume");
        Button saveGame = new Button("Save Game");
        Button exit=new Button("Exit");
        exit.setOnAction(e->
        {
            main.showMainMenu();
        });
        saveGame.setOnAction(e->
        {
            GameState gameState=new GameState(Score,getCherryCount());
            saveGameState(gameState);
        });
        resumeButton.setOnAction(e -> {
            isPaused = false;
            // Remove the options pane on clicking Resume button
            gamePane.getChildren().remove(optionsPane);
        });

        optionsPane.getChildren().addAll(resumeButton,saveGame,exit);
        optionsPane.setLayoutX(50); // Adjust the position
        optionsPane.setLayoutY(50); // Adjust the position

        // Add the options pane to the root pane
        gamePane.getChildren().add(optionsPane);

    }
    public void setScore(int Score)
    {
        score.setText(String.valueOf(Score));
    }
    public void setCherryCount(int count)
    {
        cherryCount.setText(String.valueOf(count));
    }
    public static void saveGameState(GameState gameState){

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("SaveGame.txt"))) {
            outputStream.writeObject(gameState);
//            System.out.println("game Saved");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addPlatform2() {
        double lowerBound = 300;
        double upperBound = 600;
        p2 = Platform.getPlatform();
        p2.setLayoutY(400);
        Random random = new Random();
        double x = random.nextDouble(upperBound - lowerBound + 1) + lowerBound;
        p2.setLayoutX(x);
        gamePane.getChildren().add(p2);

    }


    public void startGameLoop() {
        initialize();
        stickCreated = false;
        AnimationTimer gameLoop = new AnimationTimer() {

            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (!stopGame && !isPaused) {
                    double elapsedTime = (now - lastUpdate) / 1_000_000_000.0;
                    updateGame(elapsedTime);


                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }


    private void updateGame(double elapsedTime) {
        if (step1 && !isPaused) {
            createStick();
            addCherry();
            addObstacle();
            step1 = !step1;
            step2=true;
        }
        else if (step2 && !isPaused) {

            Scene scene = main.getScene();
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.W && !keyReleased && !isPaused) {

                    increaseStick();
                }
            });
            // Key released event handler
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.W && !keyReleased && !isPaused) {

                    step2 =false;
                    step3=true;
                    stickFall();
                }
            });
        }
        else if (step3 && !isPaused) {
            if (!checkLength()) {
                makeFall();
//                System.out.println("Game Over");
                stopGame = true;
            } else {
                step3 = !step3;
                step6=true;
            }

        }
        else if(step6)
        {
            createDelay();
        }
        else if (step4) {
            if(!cherryEaten)
            {
                eatCherry();
            }
            checkCollision();
            movePlayer();
            flipPlayer();
        } else if (step5) {
            resetScene();
//            System.out.println(Score);
        }
    }

    private void checkCollision() {
        double obsX=obstacle1.getLayoutX();
        double playerX=player.getLayoutX();
        if( (playerX+30>=obsX && playerX+30<=obsX+obstacle1.getFitWidth()) && isFlipped)
        {
            String audioPath = "/Audio/Fall.mp3";
            Media sound = new Media(getClass().getResource(audioPath).toString());
            MediaPlayer md = new MediaPlayer(sound);
            md.play();
            stopGame=true;
            main.showEndGame(Score,getCherryCount());

        }
    }

    private void addObstacle() {
        double lowerBound = 120;
        double upperBound = 280;
        obstacle1=new Obstacle1();
        obstacle1.setLayoutY(403);
        Random random=new Random();
        double x = random.nextDouble(upperBound - lowerBound + 1) + lowerBound;
        obstacle1.setLayoutX(x);
        gamePane.getChildren().add(obstacle1);
    }


    private void eatCherry() {
        double cherryX=cherry.getLayoutX();
        double playerX=player.getLayoutX();
        if((abs(playerX-cherryX)<5) && isFlipped)
        {
            String audioPath = "/Audio/points.mp3";
            Media sound = new Media(getClass().getResource(audioPath).toString());
            MediaPlayer md = new MediaPlayer(sound);
            md.play();
//            System.out.println("Is true");
            cherryCount.setText(String.valueOf(Integer.parseInt((cherryCount.getText()))+1));
            gamePane.getChildren().remove(cherry);
            cherryEaten=true;
        }
    }

    private void addCherry() {
        double lowerBound = 120;
        double upperBound = 280;
        cherry=new Cherries();
        cherry.setLayoutY(403);
        Random random=new Random();
        double x = random.nextDouble(upperBound - lowerBound + 1) + lowerBound;
        cherry.setLayoutX(x);
        gamePane.getChildren().add(cherry);
    }


    private void createDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event->
        {
            step4=true;
        });
        pause.play();
        step6=false;
    }



    private static final double MOVEMENT_SPEED = 3;
    private void movePlayer() {
        double dist=p2.getLayoutX()+p2.getWidth()-100;
        double playerX=player.getLayoutX();
        isMoving=true;
        if(playerX>=p2.getLayoutX()-40 && isFlipped)
        {
            makeFall2();
            stopGame=true;
        }
        if(playerX<dist && isMoving)
        {
            isMoving=true;
            playerX +=MOVEMENT_SPEED;
            player.setLayoutX(playerX);
        }
        else
        {
            Score+=1;
            String audioPath = "/Audio/score.mp3";
            Media scores = new Media(getClass().getResource(audioPath).toString());
            MediaPlayer scorePlayer = new MediaPlayer(scores);
            scorePlayer.play();
            score.setText(String.valueOf(Integer.parseInt(score.getText()) + 1));

            isMoving=false;
            step4 = false;
            step5=true;
        }

    }

    private void makeFall2(){

        String audioPath = "/Audio/fall.mp3";
        Media sound = new Media(getClass().getResource(audioPath).toString());
        MediaPlayer fall = new MediaPlayer(sound);
        fall.play();
        // Fall vertically after the horizontal movement completes
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), player);
        double screenHeight = 600;
        fallTransition.setToY(screenHeight);
        fallTransition.setOnFinished(event2->
        {
            main.showEndGame(Score, Integer.parseInt(cherryCount.getText()));

        });
        fallTransition.play();


    }


    private void makeFall() {

        // Move horizontally by 80 pixels
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds((80+stick.getLength())/200), player);
        moveTransition.setByX(80+stick.getLength());

        // Set up a handler to execute after the horizontal movement
        moveTransition.setOnFinished(event -> {
            String audioPath = "/Audio/fall.mp3";
            Media sound = new Media(getClass().getResource(audioPath).toString());
            MediaPlayer fall = new MediaPlayer(sound);
            fall.play();
            // Fall vertically after the horizontal movement completes
            TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), player);
            double screenHeight = 600;
            fallTransition.setToY(screenHeight);
            fallTransition.setOnFinished(event2->
            {
                main.showEndGame(Score,Integer.parseInt(cherryCount.getText()));
            });
            fallTransition.play();

        });


        pauseTransition.setOnFinished(event -> moveTransition.play());

        // Start the horizontal movement
        pauseTransition.play();


    }

    @FXML
    public void increaseStick() {
        double increment=5;
        stick.setStartY(stick.getStartY()-increment);
    }

    private void createStick() {
//        System.out.println("Stick Created");
        stick=new Stick(100,400,0);
        gamePane.getChildren().add(stick);
    }

    private boolean checkLength() {

        double p1x = 100;
        double p2x = p2.getLayoutX();
//        System.out.println(p2.getLayoutX());
//        System.out.println(p2x-p1x);
//        System.out.println(stick.getLength());
//        System.out.println(p2.getWidth());

        if (stick.getLength() >= (p2x-p1x) && stick.getLength()<=(p2x+p2.getWidth()-p1x)){
            //continue;
            return true;        }

        else {
        }

        return false;
    }


    private void stickFall() {

//        System.out.println("stick falling");

        double pivotX = stick.getEndX();
        double pivotY = stick.getEndY();

        Rotate rotate = new Rotate(0, pivotX, pivotY);
        stick.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(rotate.angleProperty(), 90))
        );

        timeline.setCycleCount(1); // Play once
        timeline.play();


    }



    public void flipPlayer() {
        Scene scene= main.getScene();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F && step4) {
                double originalHeight = player.getHeight(); //player.getheight
                double originalTranslateY = player.getTranslateY();

                if (!isFlipped) {
                    player.getTransforms().add(new Scale(1, -1, 0, originalHeight));
                    player.setTranslateY(originalTranslateY + (0.037*originalHeight));
                    isFlipped = true;
                } else {
                    player.getTransforms().clear();
                    player.setTranslateY(originalTranslateY-(0.037*originalHeight));
                    isFlipped = false;
                }
            }
        });
    }



    private void resetScene() {
        double speed=8;

        double dist= p2.getLayoutX()+p2.getWidth()-100;
        double p1x=p1.getLayoutX()-dist;
        double p2x=p2.getLayoutX()-dist;
        double stickx=stick.getLayoutX()-dist;
        double playerX=player.getLayoutX()-dist;
        double cherryX=cherry.getLayoutX()-dist;
        double obsX=obstacle1.getLayoutX()-dist;

        if (player.getLayoutX() > playerX && player.getLayoutX() - speed >= playerX) {
            player.setLayoutX(player.getLayoutX() - speed);
        }

        if (p1.getLayoutX() > p1x && p1.getLayoutX() - speed >= p1x) {
            p1.setLayoutX(p1.getLayoutX() - speed);
        }

        if (p2.getLayoutX() > p2x && p2.getLayoutX() - speed >= p2x) {
            p2.setLayoutX(p2.getLayoutX() - speed);
        }

        if (stick.getLayoutX() > stickx && stick.getLayoutX() - speed >= stickx) {
            stick.setLayoutX(stick.getLayoutX() - speed);
        }
        if (cherry.getLayoutX() > cherryX && cherry.getLayoutX() - speed >= cherryX) {
            cherry.setLayoutX(cherry.getLayoutX() - speed);
        }
        if (obstacle1.getLayoutX() > obsX && obstacle1.getLayoutX() - speed >= obsX) {
            obstacle1.setLayoutX(obstacle1.getLayoutX() - speed);
        }
        else
        {

            p1=p2;
            addPlatform2();
            stickCreated=false;
            keyReleased=false;
            step1=true;
            step2=false;
            step3=false;
            step4=false;
            step5=false;
            valve=false;
            if(!cherryEaten)
            {
                gamePane.getChildren().remove(cherry);
            }
            cherryEaten=false;
//            isMoving=false;
        }
    }

    public GameScreen() {
    }


    public void setMainApp(Main main) {
        this.main=main;
    }
}
