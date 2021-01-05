package com.potaten2015.wallz;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class BallManager {

    private ArrayList<Ball> ballsArrayList;
    private int xPos, yPos, xChange, yChange;
    private int count1;
    private int count2;
    private boolean triggered = false;
    private double oldScore = 0;
    private int ballWidth;

    public BallManager() {
        ballWidth = (int) Math.floor(MainActivity.screenWidth / 40);
        ballsArrayList = new ArrayList<Ball>();
    }

    public void newBall(ArrayList<Obstacle> obstacles, double score) {

        triggered = false;
        for(Ball ball: ballsArrayList){
            if(!ball.wasTouching && ball.touching){
                triggered = true;
            }
        }

        if (score == 0 && ballsArrayList.isEmpty() ||
                (triggered && (score % 90 == 0))) {
            count1 = 0;
            xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
            yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
            xChange = (int) Math.ceil((Math.random() * 3) + .01);
            yChange = (int) Math.ceil((Math.random() * 3) + .01);
            if (ballsArrayList.isEmpty()) {
                while (count1 < obstacles.size()) {
                    if (obstacles.get(count1).getObstacle().intersect(new Rect(this.xPos - ballWidth, this.yPos - ballWidth,
                            this.xPos + ballWidth, this.yPos + ballWidth))) {
                        count1 = 0;
                        xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
                        yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
                    } else {
                        count1++;
                    }
                }
                ballsArrayList.add(new Ball(xPos, yPos, xChange, yChange));
            }
        } else if (triggered) {
            while (count1 < ballsArrayList.size()) {
                if (ballsArrayList.get(count1).getBall().intersect(
                        new Rect(this.xPos - ballWidth, this.yPos - ballWidth,
                                this.xPos + ballWidth, this.yPos + ballWidth))) {
                    count1 = 0;
                    xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
                    yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
                } else {
                    count1++;
                    while (count2 < obstacles.size()) {
                        if ((obstacles.get(count2).getObstacle().intersect(
                                new Rect(this.xPos - ballWidth, this.yPos - ballWidth,
                                        this.xPos + ballWidth, this.yPos + ballWidth))) ||
                                (obstacles.get(count2).getObstacle().intersect(
                                        new Rect(this.xPos - ballWidth, this.yPos - ballWidth,
                                                this.xPos + ballWidth, this.yPos + ballWidth)))) {
                            count1 = 0;
                            count2 = 0;
                            xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
                            yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
                        } else {
                            count2++;
                        }
                    }
                }
            }
            ballsArrayList.add(new Ball(xPos, yPos, xChange, yChange));
        }
    }


    public ArrayList<Ball> getBallsArrayList() {

        return ballsArrayList;

    }

    public void clearBalls() {

        ballsArrayList.clear();

    }


    public void draw(Canvas canvas) {
        for (Ball ball : ballsArrayList) {
            ball.draw(canvas);
        }
    }

    public void slowBalls(double score) {

        if ((score % 5 == 0) && (score != 0) && (oldScore !=score)) {

            oldScore = score;

            for (Ball ball : ballsArrayList) {
                int oldX = ball.getxChange();
                int oldY = ball.getyChange();

                System.out.println("oldX: " + oldX);
                System.out.println("oldY: " + oldY);

                int newX = oldX - ((oldX / Math.abs(oldX)) * 4);
                int newY = oldY - ((oldY / Math.abs(oldY)) * 4);

                System.out.println("newX: " + newX);
                System.out.println("newY: " +  newY);

                ball.updatexChange(newX);
                ball.updateyChange(newY);

                System.out.println(" X Change:   " + ball.getxChange());
                System.out.println(" Y Change:   " + ball.getyChange());


            }
        }
    }

    public void update(int score) {

        slowBalls(score);

        for (Ball ball : ballsArrayList) {
            if ((ball.getxPos() < 0) || (ball.getyPos() < 0) || (ball.getxPos() > MainActivity.screenWidth) ||
                    (ball.getyPos() > MainActivity.screenHeight)) {
                ballsArrayList.remove(ball);
            }
        }

        for (Ball ball : ballsArrayList) {
            ball.update();
        }

    }
}
