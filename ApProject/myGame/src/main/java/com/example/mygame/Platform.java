package com.example.mygame;

import javafx.scene.shape.Rectangle;

import java.security.PublicKey;
import java.util.Random;

public class Platform extends Rectangle {
    private final static double widthLowBound=100;
    private final static double widthUpBound=200;
    private final static double height=200;


    private double width;
    private double startPos;
    private double endPos;

    public Platform(double width) {
        super(width,height);
        this.width = width;
    }


    public static Platform getPlatform()
    {
        Random random=new Random();
        double width=random.nextDouble(widthUpBound-widthLowBound+1)+widthLowBound;
//         this.width = width;
        return new Platform(width);
    }

    public double getStartPos() {
        return startPos;
    }

    public void setStartPos(double startPos) {
        this.startPos = startPos;
    }

    public double getEndPos() {
        return endPos;
    }

    public void setEndPos(double endPos) {
        this.endPos = endPos;
    }
}
