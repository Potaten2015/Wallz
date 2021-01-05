package com.potaten2015.wallz;

import android.view.MotionEvent;

public class MySwipeListener {

    private int x1,x2,y1,y2;

    public MySwipeListener(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

    }

    public String swipeDirection(){
        if(Math.abs(x1-x2) > Math.abs(y1-y2)){
            if((x2-x1) > 0){
                return "right";
            }
            else{
                return "left";
            }
        }
        else{
            if((y2-y1) > 0){
                return "down";
            }
            else{
                return "up";
            }
        }
    }

}
