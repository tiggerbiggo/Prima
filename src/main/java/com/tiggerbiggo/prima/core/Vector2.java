package com.tiggerbiggo.prima.core;

public class Vector2 {
    private double x, y;

    //Constructors//
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vector2(double xy)
    {
        this(xy, xy);
    }
    public Vector2(){
        this(0);
    }

    public Vector2 clone()
    {
        return new Vector2(x, y);
    }

    //Getters
    public double X() {
        return x;
    }
    public double Y() {return y;}

    public float fX() {
        return (float)x;
    }
    public float fY() {return (float)y;}

    public int iX() {
        return (int)x;
    }
    public int iY() {return (int)y;}

    //Setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }

    public float toFloat()
    {
        return (float)(x + y);
    }

    public float magnitude()
    {
        return (float)Math.sqrt((x*x)+(y*y));
    }

    public Vector2 mod(float mod){return new Vector2(x%mod, y%mod);}

    @Override
    public String toString()
    {
        return String.format("[@%s] -- X: %f, Y: %f", hashCode(), x, y);
    }

    public static Vector2 add(Vector2 a, Vector2 b)
    {
        return new Vector2(
                a.X() + b.X(),
                a.Y() + b.Y());
    }

    public static Vector2 multiply(Vector2 a, Vector2 b)
    {
        return new Vector2(
                a.X() * b.X(),
                a.Y() * b.Y());
    }

    public static Vector2 divide(Vector2 a, Vector2 b)
    {
        return new Vector2(
                a.X() / b.X(),
                a.Y() / b.Y());
    }

    public static Vector2 subtract(Vector2 a, Vector2 b)
    {
        return new Vector2(
                a.X() - b.X(),
                a.Y() - b.Y());
    }

    public static Vector2 abs(Vector2 in)
    {
        return new Vector2(
                Math.abs(in.X()),
                Math.abs(in.Y()));
    }

    public static final Vector2 ZERO = new Vector2(0,0);
    public static final Vector2 ONE = new Vector2(1,1);
}
