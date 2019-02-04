package com.tiggerbiggo.prima.primaplay.core.calculation;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents a vector in 2D space. Also contains various methods for calculation.
 */
public class Vector2 implements Serializable {

  @TransferGrid
  private double x;

  @TransferGrid
  private double y;

  /**
   * Constructs a new vector with X and Y components
   *
   * @param x The X component of the vector
   * @param y The Y component of the vector
   */
  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Shorthand constructor for when both components are the same
   *
   * @param xy Parameter for both X and Y component
   */
  public Vector2(double xy) {
    this(xy, xy);
  }

  /**
   * Default constructor, defaults to (0,0)
   */
  public Vector2() {
    this(0);
  }

  public Vector2(ComplexNumber c) {
    this(c.real, c.imaginary);
  }


  /**
   * Clones this object
   *
   * @return A new vector with the same X and Y components as the one called upon
   */
  @Override
  public Vector2 clone() {
    return new Vector2(x, y);
  }

  /**
   * Overridden toString with more descriptive info about this vector
   *
   * @return String in the format "[@<b>hash code</b>] -- X: <b>X</b>, Y: <b>Y</b>".
   */
  @Override
  public String toString() {
    return String.format("[@%s] -- X: %f, Y: %f", hashCode(), x, y);
  }

  /**
   * Sets the components of this vector
   *
   * @param x The new X component
   * @param y The new Y component
   */
  public void set(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double X() {
    return x;
  }
  public double Y() {
    return y;
  }


  public float fX() {
    return (float) x;
  }
  public float fY() {
    return (float) y;
  }

  public int iX() {
    return (int) x;
  }
  public int iY() {
    return (int) y;
  }

  /**A helper function that simply returns x + y
   * @return x + y
   */
  public double xy() {
    return x + y;
  }

  public ComplexNumber asComplex() {
    return new ComplexNumber(this);
  }

  /**Converts this vector into polar coordinates
   *
   * @return Vector2 with x = radius and y = angle. Right = 0°
   */
  public Vector2 toPolar(){
    return new Vector2(
            magnitude()
            ,
            angleBetween(RIGHT)
    );
  }


  /**
   * Gets the magnitude of the vector using Pythagorean Theorem
   *
   * @return The magnitude (length) of the vector as double
   */
  public double magnitude() {
    return Math.sqrt(sqMagnitude());
  }

  public double sqMagnitude() {
    return (x * x) + (y * y);
  }

  /**
   * Applies a modulo operation on both components of the input vector given a value
   *
   * @param mod The modulo value
   * @return The calculated number
   */
  public Vector2 mod(float mod) {
    return new Vector2(x % mod, y % mod);
  }

  /**
   * Calculates the dot product of 2 vectors
   *
   * @param other The vector to dot with
   * @return The dot product: <b>ax*bx + ay*by</b>
   */
  public double dot(Vector2 other) {
    return (this.x * other.x) + (this.y * other.y);
  }

  /**
   * Calculates the determinant of 2 vectors
   *
   * @param other The vector to det with
   * @return The determinant: <b>ax*by - ay*bx</b>
   */
  public double det(Vector2 other) {
    return (this.x * other.y) - (this.y * other.x);
  }

  /**
   * Linearly interpolates between the 2 vector components using the x and y components as separate
   * percentage values.
   *
   * @param other The vector to lerp between
   * @param p A vector representing the percentage
   * @return The calculated vector
   */
  public Vector2 lerp(Vector2 other, Vector2 p) {
    return new Vector2(Calculation.lerp(this.x, other.x, p.x),
        Calculation.lerp(this.y, other.y, p.y));
  }

  /**
   * Linearly interpolates between 2 vectors given a percentage
   *
   * @param other The vector to lerp between
   * @param p A double representing the percentage. <p>0 = 0%, 1 = 100%
   * @return The calculated vector
   */
  public Vector2 lerp(Vector2 other, double p) {
    return lerp(other, new Vector2(p));
  }

  /**
   * Gets the distance between 2 vectors
   *
   * @param other The second vector
   * @return The distance between the 2 vectors
   */
  public double distanceBetween(Vector2 other) {
    return this.subtract(other).magnitude();
  }

  /**
   * Gets the angle between 2 vectors in radians
   *
   * @param other The other vector
   * @return The angle between the 2 vectors in radians
   */
  public double angleBetween(Vector2 other) {

    double dot, det, toReturn;

    dot = dot(other);
    det = det(other);

    toReturn = Math.atan2(det, dot);

    if (toReturn < 0) {
      toReturn = (2 * Math.PI) + toReturn;
    }

    return toReturn;
  }

  /**
   * Generates a random vector which lies on a given circle, around the center of this vector.
   *
   * @param r The radius of the circle
   * @return A random vector on the circle
   */
  public Vector2 randomOnCircle(double r) {
    // Generate random angle

    double rand = ThreadLocalRandom.current().nextDouble() * Math.PI * 2;
    // Make a new vector with length of the radius of the circle which we then rotate
    Vector2 toReturn = Vector2.UP.multiply(new Vector2(r));
    toReturn = toReturn.rotateAround(Vector2.ZERO, rand);
    // Add the center point to offset it
    return toReturn.add(this);
  }

  public Vector2 multiply(double other){
    return multiply(new Vector2(other));
  }

  /**
   * Multiplies this vector by another
   *
   * @param other Second vector
   * @return a*b
   */
  public Vector2 multiply(Vector2 other) {
    return new Vector2(this.X() * other.X(), this.Y() * other.Y());
  }

  /**
   * Divides this vector by another
   *
   * @param other Second vector
   * @return a/b
   */
  public Vector2 divide(Vector2 other) {
    return new Vector2(this.X() / other.X(), this.Y() / other.Y());
  }

  /**
   * Adds this vector to another
   *
   * @param other Second vector
   * @return a+b
   */
  public Vector2 add(Vector2 other) {
    return new Vector2(this.X() + other.X(), this.Y() + other.Y());
  }

  public Vector2 add(double xy) {
    return add(new Vector2(xy));
  }

  /**
   * Subtracts this vector from another
   *
   * @param other Second vector
   * @return a-b
   */
  public Vector2 subtract(Vector2 other) {
    return new Vector2(this.X() - other.X(), this.Y() - other.Y());
  }

  /**
   * @return Absolute value of the input vector where both components are positive
   */
  public Vector2 abs() {
    return new Vector2(Math.abs(this.X()), Math.abs(this.Y()));
  }

  /**
   * Returns a normalized (magnitude = 1) copy of a given Vector
   *
   * <p>Special Cases:
   *
   * <ul> <li>If the magnitude of the given vector is 0, the result will be Vector2.ZERO (0,0)
   * </ul>
   *
   * @return The normalized vector
   */
  public Vector2 normalize() {
    double magnitude = magnitude();
    if (magnitude == 0) {
      return Vector2.ZERO;
    }
    return new Vector2(x / magnitude, y / magnitude);
  }

  /**
   * Rotates a given vector around a point and returns the result
   *
   * @param rotatePoint The point to rotate around
   * @param angleRadians The angle to rotate in radians
   * @return The rotated vector
   */
  public Vector2 rotateAround(Vector2 rotatePoint, double angleRadians) {
    Vector2 in = this.subtract(rotatePoint);

    double sin, cos;
    sin = Math.sin(angleRadians);
    cos = Math.cos(angleRadians);

    in = new Vector2((in.x * cos) - (in.y * sin), (in.x * sin) + (in.y * cos));
    in = in.add(rotatePoint);
    return in;
  }

  /**Makes a new Vector2 from polar coordinates,
   * using the X component of this vector as the angle
   * and the Y component as the radius. <br>
   *
   * Calculations are in Radians.
   */
  public Vector2 fromPolar(){
    return new Vector2(Math.sin(x), Math.cos(y));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector2 vector2 = (Vector2) o;
    return Double.compare(vector2.x, x) == 0 &&
        Double.compare(vector2.y, y) == 0;
  }

  public static Vector2[] blankArray(int n){
    Vector2[] toReturn = new Vector2[n];
    for(int i=0; i<n; i++){
      toReturn[i] = ZERO;
    }
    return toReturn;
  }

  public static Vector2[] simpleCycle(int n){
    Vector2[] toReturn = new Vector2[n];
    for(int i=0; i<n; i++){
      double m = (double)i/n;
      toReturn[i] = new Vector2(m/2, m/2);
    }
    return toReturn;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  public static final Vector2 ZERO = new Vector2(0);
  public static final Vector2 ONE = new Vector2(1);
  public static final Vector2 MINUSONE = new Vector2(-1);
  public static final Vector2 TWO = new Vector2(2);
  public static final Vector2 MINUSTWO = new Vector2(-2);
  public static final Vector2 UP         = new Vector2(0, 1);
  public static final Vector2 DOWN       = new Vector2(0, -1);
  public static final Vector2 LEFT       = new Vector2(-1, 0);
  public static final Vector2 RIGHT      = new Vector2(1, 0);
}
