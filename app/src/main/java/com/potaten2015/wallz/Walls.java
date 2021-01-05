package com.potaten2015.wallz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Walls {

    private Rect botWall;
    private Rect topWall;
    private Rect leftWall;
    private Rect rightWall;
    private ArrayList<Walls> wallsArrayList;
    private int wallWidth;

    private Paint paint;

    private static String objectType;

    private String dir;

    public Walls(){
        wallWidth = (int) Math.floor(MainActivity.screenWidth / 20);

        leftWall = new Rect(0,0,wallWidth,MainActivity.screenHeight);
        topWall = new Rect(0,0,MainActivity.screenWidth,wallWidth);
        rightWall = new Rect(MainActivity.screenWidth-wallWidth,0,MainActivity.screenWidth,MainActivity.screenHeight);
        botWall = new Rect(0,MainActivity.screenHeight-wallWidth,MainActivity.screenWidth,MainActivity.screenHeight);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        objectType = "Walls";
    }


    //FOR TESTING PURPOSES ONLY
    public void forTesting(Paint paint){
        // this.paint = paint;
    }

    public void switchColor(int x1, int y1, int x2, int y2){

        if(Math.abs(x2-x1) > Math.abs(y2-y1)){
            if(x2 > x1){
                dir = "right";
            }
            else{
                dir = "left";
            }
        }
        else{
            if(y2 > y1){
                dir = "down";
            }
            else{
                dir = "up";
            }
        }


        switch (dir){
            case "left":
                paint.setColor(Color.BLUE);
                break;
            case "right":
                paint.setColor(Color.BLACK);
                break;
            case "up":
                paint.setColor(Color.GREEN);
                break;
            case "down":
                paint.setColor(Color.RED);
                break;
        }

    }

    public Rect getTopWall(){
        return topWall;
    }

    public Rect getBotWall() {
        return botWall;
    }

    public Rect getLeftWall() {
        return leftWall;
    }

    public Rect getRightWall() {
        return rightWall;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(leftWall,paint);
        canvas.drawRect(topWall,paint);
        canvas.drawRect(rightWall,paint);
        canvas.drawRect(botWall,paint);
    }



    public void update(){

    }

    public ArrayList<Walls> getWallsArrayList() {
        return wallsArrayList;
    }

    public Paint getPaint(){
        return paint;
    }
}
