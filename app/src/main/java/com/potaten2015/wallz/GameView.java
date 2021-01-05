package com.potaten2015.wallz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private Paint paint;
    private ArrayList allObjects;

    private Walls walls;
    private BallManager ballManager;
    private ObstacleManager obstacleManager;

    private boolean justTouched;
    public boolean starting;
    int x1, x2, y1, y2;

    private double score;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        walls = new Walls();
        starting = true;
        obstacleManager = new ObstacleManager();
        ballManager = new BallManager();
        score = 0;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                x1 = (int) event.getX();
                y1 = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x2 = (int) event.getX();
                y2 = (int) event.getY();

                walls.switchColor(x1,y1,x2,y2);
        }


        return true;

    }

    @Override
    public void run() {
        while (isPlaying) {

            update();
            draw();
            sleep();

        }
    }

    private void update() {

        if(starting) {
            ballManager.newBall(obstacleManager.getObstaclesArrayList(), 0);
        }
        else{
            ballManager.newBall(obstacleManager.getObstaclesArrayList(), score);
        }
        walls.update();

        if(ballManager.getBallsArrayList().size() >0){
            walls.forTesting(ballManager.getBallsArrayList().get(0).getPaint());
        }


        //Ball Updates
        for(Ball ball: ballManager.getBallsArrayList()){
            // ball.checkBallIntersect(ballManager.getBallsArrayList());
            // ball.checkObstacleIntersect(obstacleManager.getObstaclesArrayList());


            if(ball.checkWallIntersect(walls.getLeftWall(),walls.getTopWall(),walls.getRightWall(),
                    walls.getBotWall(),walls.getPaint())){
                score++;
            }
        }

        ballManager.update((int)score);

        obstacleManager.update(ballManager.getBallsArrayList(),score);

        if(ballManager.getBallsArrayList().size() < 1){
            Intent intent = new Intent(this.getContext(),HighScoreScreen.class);
            intent.putExtra("score",(int)score);
            score = 0;
            starting = true;
            walls = new Walls();
            obstacleManager = new ObstacleManager();
            ballManager = new BallManager();
            this.getContext().startActivity(intent);
            isPlaying = false;
        }



    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            //DRAW OBJECTS HERE
            paint = new Paint();
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, screenX, screenY, paint);
            walls.draw(canvas);
            obstacleManager.draw(canvas);
            ballManager.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void updateArrays() {

    }

    public double getScore(){
        return score;
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}