package com.tiggerbiggo.prima.core;

public class float2 {
    private float x, y;

    //Constructors//
    public float2(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float2(){
        this(0, 0);
    }

    //Getters
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    //Setters
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString()
    {
        return String.format("X: %f, Y: %f", x, y);
    }

    public static final float2 ZERO = new float2(0,0);
    public static final float2 ONE = new float2(1,1);
}
