package com.example.mygame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private static final double yPos=350;
    private static final double speed=2;
    private double xPos=20;
    private static Player instance ;
    public static Player getInstance(){
        if (instance == null) {
            instance = new Player();
        }
//         instance.setLayoutX(100);
//         instance.setLayoutY(100);
        return instance;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos()
    {
        return yPos;
    }

    public void flip(){

    }

    public void move(double steps){
        this.setLayoutX(this.getLayoutX()+steps);
        
    }
    public double getHeight()
    {
        return this.getFitHeight();
    }
    public Player() {
        String imagePath = "/images/newPlayer.png"; // Relative path from the resources folder

        // Load the image for the player
        Image playerImage = new Image(getClass().getResourceAsStream(imagePath));
        setImage(playerImage);

        // Set other properties or behaviors for the player if needed
        setFitWidth(50);
        setFitHeight(50);
    }


}
