package com.tiggerbiggo.prima.core;

public class float2 {
    private float x, y;

    //Constructors//
    public float2(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float2(float xy)
    {
        this(xy, xy);
    }
    public float2(){
        this(0);
    }

    //Getters
    public float X() {
        return x;
    }
    public float Y() {
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

    public float toFloat()
    {
        return x + y;
    }

    public float magnitude()
    {
        return (float)Math.sqrt((x*x)+(y*y));
    }

    public float2 mod(float mod){return new float2(x%mod, y%mod);}

    @Override
    public String toString()
    {
        return String.format("X: %f, Y: %f", x, y);
    }

    public static float2 add(float2 a, float2 b)
    {
        return new float2(
                a.X() + b.X(),
                a.Y() + b.Y());
    }

    public static float2 multiply(float2 a, float2 b)
    {
        return new float2(
                a.X() * b.X(),
                a.Y() * b.Y());
    }

    public static float2 subtract(float2 a, float2 b)
    {
        return new float2(
                a.X() - b.X(),
                a.Y() - b.Y());
    }

    public static float2 abs(float2 in)
    {
        return new float2(
                Math.abs(in.X()),
                Math.abs(in.Y()));
    }

    public static final float2 ZERO = new float2(0,0);
    public static final float2 ONE = new float2(1,1);
}
