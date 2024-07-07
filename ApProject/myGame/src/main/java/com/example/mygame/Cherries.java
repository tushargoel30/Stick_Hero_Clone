package com.example.mygame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherries extends ImageView {
    private int cherries_number;
    private int cherry_pos;

    public Cherries() {
        String imagePath = "/images/cherry.png"; // Relative path from the resources folder

        // Load the image for the player
        setImage(new Image(getClass().getResourceAsStream(imagePath)));

        // Set other properties or behaviors for the player if needed
        setFitWidth(30);
        setFitHeight(30);
    }

    public int getCherries_number() {
        return cherries_number;
    }

    public void setCherries_number(int cherries_number) {
        this.cherries_number = cherries_number;
    }

    public int getCherry_pos() {
        return cherry_pos;
    }

    public void setCherry_pos(int cherry_pos) {
        this.cherry_pos = cherry_pos;
    }
}
