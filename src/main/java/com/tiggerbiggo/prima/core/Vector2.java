package com.tiggerbiggo.prima.core;


/**
 * Represents a vector in 2D space. Also contains various methods for calculation.
 */
public class Vector2 {
    private double x, y;

    /**
     * Constructs a new vector with X and Y components
     * @param x The X component of the vector
     * @param y The Y component of the vector
     */
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Shorthand constructor for when both components are the same
     * @param xy Parameter for both X and Y component
     */
    public Vector2(double xy)
    {
        this(xy, xy);
    }

    /**
     * Default constructor, defaults to (0,0)
     */
    public Vector2(){
        this(0);
    }

    @Override
    public Vector2 clone()
    {
        return new Vector2(x, y);
    }
    @Override
    public String toString()
    {
        return String.format("[@%s] -- X: %f, Y: %f", hashCode(), x, y);
    }

    /**
     * @return The x component as double
     */
    public double X() {
        return x;
    }

    /**
     * @return The y component as double
     */
    public double Y() {return y;}

    /**
     * @return The x component as float
     */
    public float fX() {
        return (float)x;
    }
    /**
     * @return The y component as float
     */
    public float fY() {return (float)y;}

    /**
     * @return The x component as integer
     */
    public int iX() {
        return (int)x;
    }
    /**
     * @return The y component as integer
     */
    public int iY() {return (int)y;}


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

    /**
     * @return The magnitude (length) of the vector as double
     */
    public double magnitude()
    {
        return Math.sqrt((x*x)+(y*y));
    }

    /**Applies a modulo operation on both components of the imput vector given a value
     *
     * @param in The vector to apply the modulo operator to
     * @param mod The modulo value
     * @return The calculated number
     */
    public static Vector2 mod(Vector2 in, float mod){return new Vector2(in.x%mod, in.y%mod);}

    /**
     * @return New vector as negative of the input.
     */
    public Vector2 minus(){return new Vector2(-x, -y);}


    /**
     * Adds 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a+b
     */
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() + b.X(),
                a.Y() + b.Y());
    }

    /**
     * Multiplies 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a*b
     */
    public static Vector2 multiply(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() * b.X(),
                a.Y() * b.Y());
    }

    /**
     * Divides 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a/b
     */
    public static Vector2 divide(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() / b.X(),
                a.Y() / b.Y());
    }

    /**
     * Subtracts 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a-b
     */
    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() - b.X(),
                a.Y() - b.Y());
    }

    /**
     * @param in Vector to calculate
     * @return input vector where both components are positive
     */
    public static Vector2 abs(Vector2 in) {
        return new Vector2(
                Math.abs(in.X()),
                Math.abs(in.Y()));
    }

    public static final Vector2 ZERO = new Vector2(0);
    public static final Vector2 ONE = new Vector2(1);
    public static final Vector2 MINUSONE = new Vector2(-1);
    public static final Vector2 TWO = new Vector2(2);
    public static final Vector2 MINUSTWO = new Vector2(-2);
}
