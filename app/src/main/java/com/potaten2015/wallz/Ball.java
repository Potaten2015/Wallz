package com.potaten2015.wallz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Ball {

    private Rect ball;
    private Paint paint;
    private int xPos, yPos, xChange, yChange;
    private int colorNum;
    private static String objectType;
    public boolean touching = false;
    public boolean wasTouching = false;
    private int ballWidth;

    public Ball(int xPos, int yPos, int xChange, int yChange) {

        ballWidth = (int) Math.floor(MainActivity.screenWidth / 40);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xChange = xChange;
        this.yChange = yChange;
        ball = new Rect(this.xPos - ballWidth, this.yPos - ballWidth, this.xPos + ballWidth, this.yPos + ballWidth);
        ball.sort();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        objectType = "Ball";
    }

    public int getBallWidth() {return ballWidth;}

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getxChange() {
        return xChange;
    }

    public int getyChange() {
        return yChange;
    }

    private void switchBallColor() {
        colorNum = (int) Math.ceil(Math.random() * 4);

        switch (colorNum) {
            case 1:
                paint.setColor(Color.BLACK);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                break;
            case 3:
                paint.setColor(Color.RED);
                break;
            case 4:
                paint.setColor(Color.GREEN);
                break;

        }

    }

    public boolean checkWallIntersect(Rect leftWall, Rect topWall, Rect rightWall, Rect botWall, Paint paint) {
        wasTouching = touching;
        if ((!wasTouching) && ((ball.intersect(leftWall) || ball.intersect(rightWall)) && (this.paint.getColor() == paint.getColor()))) {
            xChange *= -1;
            switchBallColor();
            increaseSpeed();
            touching = true;
            return true;
        } else if ((!wasTouching) && ((ball.intersect(topWall) || ball.intersect(botWall)) && (this.paint.getColor() == paint.getColor()))) {
            yChange *= -1;
            switchBallColor();
            increaseSpeed();
            touching = true;
            return true;
        } else if ((wasTouching) && ((ball.intersect(leftWall) || ball.intersect(rightWall) || ball.intersect(topWall) || ball.intersect(botWall))) && (this.paint.getColor() == paint.getColor())){
            touching = true;
            return false;
        }
        else {
            touching = false;
            return false;
        }
    }

    /*
    public void checkBallIntersect(ArrayList ballArrayList) {

    }
    */

    /*

    }public void checkObstacleIntersect(ArrayList<Obstacle> obstacleArrayList) {
        wasTouching = touching;
        for (Obstacle obstacle : obstacleArrayList) {
            if ((!wasTouching) && ((ball.intersect(obstacle.getObstacle()) ))) {
                xChange *= -1;
                switchBallColor();
                increaseSpeed();
                touching = true;
            } else if ((wasTouching) && ((ball.intersect(obstacle.getObstacle()) ))){
                touching = true;
            } else {
                touching = false;
            }
        }
    */

    public void increaseSpeed() {
        xChange += Math.abs(xChange) / xChange;
        yChange += Math.abs(yChange) / yChange;
    }

    public String getObjectType() {
        return objectType;
    }


    public Rect getBall() {
        return ball;
    }

    private void increment() {
        xPos += xChange;
        yPos += yChange;
    }

    public void updatexChange(int xChange) {
        this.xChange = xChange;
    }

    public void updateyChange(int yChange) {
        this.yChange = yChange;
    }

    public void update() {
        increment();
        ball.set(this.xPos - ballWidth, this.yPos - ballWidth, this.xPos + ballWidth, this.yPos + ballWidth);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(ball, paint);
    }

    public Paint getPaint() {
        return paint;
    }
}
