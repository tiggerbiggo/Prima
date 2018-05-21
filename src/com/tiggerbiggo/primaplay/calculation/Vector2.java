package com.tiggerbiggo.primaplay.calculation;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a vector in 2D space. Also contains various methods for calculation.
 */
public class Vector2 implements Serializable{
    private double x, y;

    /**Constructs a new vector with X and Y components
     * @param x The X component of the vector
     * @param y The Y component of the vector
     */
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**Shorthand constructor for when both components are the same
     * @param xy Parameter for both X and Y component
     */
    public Vector2(double xy)
    {
        this(xy, xy);
    }

    /**Default constructor, defaults to (0,0)*/
    public Vector2(){
        this(0);
    }

    /**Clones this object
     * @return A new vector with the same X and Y components as the one called upon
     */
    @Override
    public Vector2 clone()
    {
        return new Vector2(x, y);
    }

    /** Overridden toString with more descriptive info about this vector
     * @return String in the format "[@<b>hash code</b>] -- X: <b>X</b>, Y: <3b>Y</b>".
     */
    @Override
    public String toString()
    {
        return String.format("[@%s] -- X: %f, Y: %f", hashCode(), x, y);
    }

    /**Sets the components of this vector
     * @param x The new X component
     * @param y The new Y component
     */
    public void set(double x, double y){this.x = x; this.y = y;}

    /** Getter for the x component
     * @return The x component as double
     */
    public double X() {
        return x;
    }

    /**Getter for the y component
     * @return The y component as double
     */
    public double Y() {return y;}

    /**Getter for the x component cast to float
     * @return The x component as float
     */
    public float fX() {return (float)x;}

    /**Getter for the y component cast to float
     * @return The y component as float
     */
    public float fY() {return (float)y;}

    /**Getter for the x component cast to integer
     * @return The x component as integer
     */
    public int iX() {return (int)x;}

    /**Getter for the y component cast to integer
     * @return The y component as integer
     */
    public int iY() {return (int)y;}

    /**Gets the magnitude of the vector using Pythagorean Theorem
     * @return The magnitude (length) of the vector as double
     */
    public double magnitude() {return Math.sqrt((x*x)+(y*y));}

    /**Applies a modulo operation on both components of the imput vector given a value
     *
     * @param in The vector to apply the modulo operator to
     * @param mod The modulo value
     * @return The calculated number
     */
    public static Vector2 mod(Vector2 in, float mod){return new Vector2(in.x%mod, in.y%mod);}

    /**Calculates the dot product of 2 vectors
     *
     * @param a Vector A
     * @param b Vector B
     * @return The dot product: <b>ax*bx + ay*by</b>
     */
    public static double dot(Vector2 a, Vector2 b) {return (a.x*b.x) + (a.y*b.y);}

    /**Calculates the determinant of 2 vectors
     *
     * @param a Vector A
     * @param b Vector B
     * @return The determinant: <b>ax*by - ay*bx</b>
     */
    public static double det(Vector2 a, Vector2 b) {return (a.x*b.y) - (a.y*b.x);}

    /**Linearly interpolates between 2 vectors given a percentage
     *
     * @param a The first vector
     * @param b The second vector
     * @param p A double representing the percentage. <p>0 = 0%, 1 = 100%</p>
     * @return The calculated vector
     */
    public static Vector2 lerpVector(Vector2 a, Vector2 b, double p){
        return new Vector2(
                Calculation.lerp(a.x, b.x, p),
                Calculation.lerp(a.y, b.y, p)
        );
    }

    /**Gets the distance between 2 vectors
     *
     * @param a The first Vector
     * @param b The second Vector
     * @return The distance between the 2 vectors
     */
    public static double distanceBetween(Vector2 a, Vector2 b){return subtract(a, b).magnitude();}

    /**Gets the angle between 2 vectors in radians
     *
     * @param vecA The first Vector
     * @param vecB The second Vector
     * @return The angle between the 2 vectors in radians
     */
    public static double angleBetween(Vector2 vecA, Vector2 vecB) {

        double dot, det, toReturn;

        dot = dot(vecA, vecB);
        det = det(vecA, vecB);

        toReturn = Math.atan2(det, dot);

        if(toReturn < 0) toReturn = (2*Math.PI)+toReturn;

        return toReturn;
    }

    /**Generates a random vector which lies on a given circle.
     *
     * @param center The center point of the circle
     * @param r The radius of the circle
     * @return A random vector on the circle
     */
    public static Vector2 randomOnCircle(Vector2 center, double r){
        //Generate random angle
        double rand = new Random().nextDouble()*Math.PI*2;
        //Make a new vector with length of the radius of the circle which we then rotate
        Vector2 toReturn = Vector2.multiply(Vector2.UP, new Vector2(r));
        toReturn = Vector2.rotateAround(toReturn, Vector2.ZERO, rand);
        //Add the center point to offset it
        return Vector2.add(toReturn, center);
    }

    /**Multiplies 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a*b
     */
    public static Vector2 multiply(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() * b.X(),
                a.Y() * b.Y());
    }

    /**Divides 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a/b
     */
    public static Vector2 divide(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() / b.X(),
                a.Y() / b.Y());
    }

    /**Adds 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a+b
     */
    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() + b.X(),
                a.Y() + b.Y());
    }

    /**Subtracts 2 vectors
     * @param a First vector
     * @param b Second vector
     * @return a-b
     */
    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(
                a.X() - b.X(),
                a.Y() - b.Y());
    }

    /**@param in Vector to calculate
     * @return Absolute value of the input vector where both components are positive
     */
    public static Vector2 abs(Vector2 in) {
        return new Vector2(
                Math.abs(in.X()),
                Math.abs(in.Y()));
    }

    /**<p>Returns a normalized (magnitude = 1) copy of a given Vector
     *
     * <p>Special Cases:
     *  <ul>
     *     <li>If the magnitude of the given vector is 0, the result will be Vector2.ZERO (0,0)</li>
     *     <li>If the given vector is null, the result will also be null</li>
     *  </ul>
     *
     * @param in The vector to normalize
     * @return The normalized vector
     */
    public static Vector2 normalize(Vector2 in) {
        if(in == null)return null;
        double magnitude = in.magnitude();
        if(magnitude == 0) return Vector2.ZERO;
        return new Vector2(in.x/magnitude, in.y/magnitude);
    }

    /**Rotates a given vector around a point and returns the result
     *
     * @param in The vector to rotate
     * @param rotatePoint The point to rotate around
     * @param angleRadians The angle to rotate in radians
     * @return The rotated vector
     */
    public static Vector2 rotateAround(Vector2 in, Vector2 rotatePoint, double angleRadians) {
        in = Vector2.subtract(in, rotatePoint);

        double sin, cos;
        sin = Math.sin(angleRadians);
        cos = Math.cos(angleRadians);

        in = new Vector2(
                (in.x * cos) - (in.y * sin),
                (in.x * sin) + (in.y * cos)
        );
        in = Vector2.add(in, rotatePoint);
        return in;
    }

    public static final Vector2 UP = new Vector2(1, 0);
    public static final Vector2 ZERO = new Vector2(0);
    public static final Vector2 ONE = new Vector2(1);
    public static final Vector2 MINUSONE = new Vector2(-1);
    public static final Vector2 TWO = new Vector2(2);
    public static final Vector2 MINUSTWO = new Vector2(-2);
}
