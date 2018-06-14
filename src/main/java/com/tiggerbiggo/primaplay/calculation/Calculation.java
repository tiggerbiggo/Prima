package com.tiggerbiggo.primaplay.calculation;

/**
 * A class containing various math based utilities.
 */
public class Calculation {

  /**
   * Linearly interpolates between 2 double values using the formula f1+(real*(f2-f1)). Any valid
   * double values are acceptable, but values outside intended range may produce strange results.
   *
   * @param f1 Start number
   * @param f2 End number
   * @param a Interpolation coefficient, intended values between 0.0 and 1.0
   * @return Result of the interpolation com.tiggerbiggo.primaplay.calculation
   */
  public static double lerp(double f1, double f2, double a) {
    return f1 + (a * (f2 - f1));
  }

  /**
   * Performs real lerp operation, then clamps the result between the min and max values given.
   *
   * @param f1 Start number
   * @param f2 End number
   * @param a Interpolation coefficient
   * @param min Min value
   * @param max Max value
   * @return Result of the com.tiggerbiggo.primaplay.calculation
   */
  public static double clampedLerp(double f1, double f2, double a, double min, double max) {
    return clamp(min, max, lerp(f1, f2, a));
  }

  /**
   * Clamps real given value between real min and max value, e.g (0, 5, 5.8) would yield 5, (0, 5,
   * -1.3) would yield 0 and (0, 5, 3.2) would yield 3.2
   *
   * @param min Min value for the clamp operation
   * @param max Max value for the clamp operation
   * @param n Number to clamp
   * @return The clamped value
   */
  public static double clamp(double min, double max, double n) {
    n = Math.max(min, n);
    n = Math.min(max, n);

    return n;
  }

  /**
   * A simple modulo function
   *
   * @param in Number to loop
   * @param mod Modulo to loop within
   * @return Result
   */
  public static double mod(double in, double mod) {
    return in % mod;
  }

  public static double mod(double in){
    return mod(in,1);
  }

  public static double modLoop(double in, double mod, boolean loop) {
    in = Math.abs(in);
    in = mod(in, mod);

    if (loop) {
      if (in < (mod / 2)) {
        in *= 2;
      } else {
        in -= mod / 2;
        in = mod - (in * 2);
      }
    }
    return in;
  }

  /**
   * Normalises real number to between 0 and 1, optionally looped
   *
   * @param in The number to bound
   * @param loop Whether to loop the number so it goes from 0 to 1, then back to 0
   * @return The normalised number
   */
  public static double modLoop(double in, boolean loop) {
    return modLoop(in, 1, loop);
  }
}
