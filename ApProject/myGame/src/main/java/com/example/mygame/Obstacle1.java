package com.example.mygame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle1 extends ImageView {
    public Obstacle1()
    {
        String imagePath = "/images/obstacle1.png"; // Relative path from the resources folder

        // Load the image for the player
        Image obsImage = new Image(getClass().getResourceAsStream(imagePath));
        setImage(obsImage);

        // Set other properties or behaviors for the player if needed
        setFitWidth(30);
        setFitHeight(30);
    }

}
