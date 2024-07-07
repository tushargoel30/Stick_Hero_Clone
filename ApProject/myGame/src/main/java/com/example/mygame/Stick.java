package com.example.mygame;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.animation.RotateTransition;

import static java.lang.Math.abs;

public class Stick extends Line {
    
    
    private double startX;
    private final static double startY=400;




    public void fall(){

    }



//    public Stick(double startX, double endX, double endY) {
//        line=new Line(startX, 400, endX, endY);
//        this.startX=startX;
//        length=endY-startY;
//    }




    public double getLength() {
        return abs(this.getStartY()-this.getEndY());
    }

    public Stick(double startX, double startY, double length) {
        // Set the coordinates for the vertical line
        setStartX(startX);
        setStartY(startY);
        setEndX(startX); // Vertical line will be drawn from startX to startY + length
        setEndY(startY + length);
//        double pivotX = startX;
//        double pivotY = startY;
//
//        // Create a Rotate transformation around the pivot point
//        Rotate rotate = new Rotate(180, pivotX, pivotY);
//        getTransforms().add(rotate);

        setStrokeWidth(3); // Set the line width
    }



}
