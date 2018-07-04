package com.tiggerbiggo.primaplay.calculation;

import ch.rs.reflectorgrid.TransferGrid;
import java.io.Serializable;
import java.util.Random;

/** Represents a vector in 2D space. Also contains various methods for calculation. */
public class Vector2 implements Serializable {

  @TransferGrid
  private double x, y;

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

  public Vector2(ComplexNumber c) {
    this(c.real, c.imaginary);
  }

  /** Default constructor, defaults to (0,0) */
  public Vector2() {
    this(0);
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
   * @return String in the format "[@<b>hash code</b>] -- X:
   *     <b>X</b>, Y: <3b>Y</b>".
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

  /**
   * Getter for the x component
   *
   * @return The x component as double
   */
  public double X() {
    return x;
  }

  /**
   * Getter for the y component
   *
   * @return The y component as double
   */
  public double Y() {
    return y;
  }

  /**
   * Getter for the x component cast to float
   *
   * @return The x component as float
   */
  public float fX() {
    return (float) x;
  }

  /**
   * Getter for the y component cast to float
   *
   * @return The y component as float
   */
  public float fY() {
    return (float) y;
  }

  /**
   * Getter for the x component cast to integer
   *
   * @return The x component as integer
   */
  public int iX() {
    return (int) x;
  }

  /**
   * Getter for the y component cast to integer
   *
   * @return The y component as integer
   */
  public int iY() {
    return (int) y;
  }

  public double xy(){return x + y;}

  public ComplexNumber asComplex() {
    return new ComplexNumber(this);
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
   * Linearly interpolates between the 2 vector components
   * using the x and y components as separate percentage values.
   *
   * @param other The vector to lerp between
   * @param p A vector representing the percentage
   * @return The calculated vector
   */
  public Vector2 lerpVector(Vector2 other, Vector2 p) {
    return new Vector2(Calculation.lerp(this.x, other.x, p.x), Calculation.lerp(this.y, other.y, p.y));
  }

  /**
   * Linearly interpolates between 2 vectors given a percentage
   *
   * @param other The vector to lerp between
   * @param p A double representing the percentage.
   *     <p>0 = 0%, 1 = 100%
   * @return The calculated vector
   */
  public Vector2 lerpVector(Vector2 other, double p) {
    return lerpVector(other, new Vector2(p));
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
   * Generates a random vector which lies on a given circle, the center of which is <code>this
   * </code>.
   *
   * @param r The radius of the circle
   * @return A random vector on the circle
   */
  public Vector2 randomOnCircle(double r) {
    // Generate random angle
    double rand = new Random().nextDouble() * Math.PI * 2;
    // Make a new vector with length of the radius of the circle which we then rotate
    Vector2 toReturn = Vector2.UP.multiply(new Vector2(r));
    toReturn = toReturn.rotateAround(Vector2.ZERO, rand);
    // Add the center point to offset it
    return toReturn.add(this);
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

  public Vector2 add(double xy){return add(new Vector2(xy));}

  /**
   * Subtracts this vector from another
   *
   * @param other Second vector
   * @return a-b
   */
  public Vector2 subtract(Vector2 other) {
    return new Vector2(this.X() - other.X(), this.Y() - other.Y());
  }

  /** @return Absolute value of the input vector where both components are positive */
  public Vector2 abs() {
    return new Vector2(Math.abs(this.X()), Math.abs(this.Y()));
  }

  /**
   * Returns a normalized (magnitude = 1) copy of a given Vector
   *
   * <p>Special Cases:
   *
   * <ul>
   *   <li>If the magnitude of the given vector is 0, the result will be Vector2.ZERO (0,0)
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

  public static final Vector2 UP = new Vector2(1, 0);
  public static final Vector2 ZERO = new Vector2(0);
  public static final Vector2 ONE = new Vector2(1);
  public static final Vector2 MINUSONE = new Vector2(-1);
  public static final Vector2 TWO = new Vector2(2);
  public static final Vector2 MINUSTWO = new Vector2(-2);
}
