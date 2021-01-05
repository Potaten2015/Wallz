package com.potaten2015.wallz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.icu.text.TimeZoneFormat;

public class Obstacle {

    private static String objectType;
    private Rect obstacle;
    private int xPos, yPos;
    private Paint paint;
    private int color;

    public Obstacle(int xPos, int yPos, double score){
        this.xPos = xPos;
        this.yPos = yPos;
        objectType = "Obstacle";
        paint = new Paint();
        switchObstacleColor(score);
        obstacle = new Rect(this.xPos-50,this.yPos-50,this.xPos+50,this.yPos+50);
        obstacle.sort();
    }

    public Rect getObstacle(){
        return obstacle;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(obstacle,paint);
    }

    public void switchObstacleColor(double score){
        if(score < 40){
            color = 1;
        }
        else if(score < 60){
            color = 2;
        }
        else if(score < 80){
            color = 3;
        }
        else if(score < 100){
            color = 4;
        }
        else{
            color = (int) Math.ceil(Math.random()*3.9 + .01);
        }

        setColor(color);
    }

    public void setColor(int color){
        switch (color){
            case 1:
                paint.setColor(Color.CYAN);
                break;
            case 2:
                paint.setColor(Color.MAGENTA);
                break;
            case 3:
                paint.setColor(Color.YELLOW);
                break;
            case 4:
                paint.setColor(Color.GRAY);
                break;
            case 5:

        }
    }

    public void update(){

    }

}
