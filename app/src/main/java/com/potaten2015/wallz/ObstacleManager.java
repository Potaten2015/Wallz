package com.potaten2015.wallz;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class ObstacleManager {

    private ArrayList<Obstacle> obstaclesArrayList;
    private int xPos, yPos, count1, count2;
    private boolean triggered;

    public ObstacleManager() {
        obstaclesArrayList = new ArrayList<Obstacle>();
    }

    public void newObstacle(ArrayList<Ball> balls, double score) {

        triggered = false;
        for(Ball ball: balls){
            if(!ball.wasTouching && ball.touching){
                triggered = true;
            }
        }

        if (triggered && (score > 20 && score % 2 == 0)) {
            count1 = 0;
            xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
            yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
            if (obstaclesArrayList.isEmpty()) {
                while (count1 < balls.size()) {
                    if (balls.get(count1).getBall().intersect(new Rect(this.xPos - 50, this.yPos - 50,
                            this.xPos + 50, this.yPos + 50))) {
                        count1 = 0;
                        xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
                        yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
                    } else {
                        count1++;
                    }
                }
                // obstaclesArrayList.add(new Obstacle(xPos, yPos, score));
            } else {
                while (count1 < obstaclesArrayList.size()) {
                    if (obstaclesArrayList.get(count1).getObstacle().intersect(
                            new Rect(this.xPos - 50, this.yPos - 50,
                                    this.xPos + 50, this.yPos + 50))) {
                        count1 = 0;
                        xPos = (int) Math.round(Math.random() * (MainActivity.screenWidth - 1000) + 500);
                        yPos = (int) Math.round(Math.random() * (MainActivity.screenHeight - 1000) + 500);
                    } else {
                        count1++;
                        while (count2 < balls.size()) {
                            if ((balls.get(count2).getBall().intersect(
                                    new Rect(this.xPos - 50, this.yPos - 50,
                                            this.xPos + 50, this.yPos + 50))) ||
                                    (balls.get(count2).getBall().intersect(
                                            new Rect(this.xPos - 50, this.yPos - 50,
                                                    this.xPos + 50, this.yPos + 50)))) {
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
                // obstaclesArrayList.add(new Obstacle(xPos, yPos, score));
            }
        }
    }

    public ArrayList<Obstacle> getObstaclesArrayList() {
        return obstaclesArrayList;
    }

    public void clearObstacles() {
        obstaclesArrayList.clear();
    }

    public void draw(Canvas canvas) {
        for (Obstacle obstacle : obstaclesArrayList) {
            obstacle.draw(canvas);
        }
    }

    public void update(ArrayList balls, double score) {

        if (obstaclesArrayList.size() > 10) {
            clearObstacles();
        }
        newObstacle(balls, score);

        for (Obstacle obstacle : obstaclesArrayList) {
            obstacle.update();
        }
    }

}
