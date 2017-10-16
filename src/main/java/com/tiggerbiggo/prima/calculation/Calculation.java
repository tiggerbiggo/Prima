package com.tiggerbiggo.prima.calculation;

public class Calculation {


    /**
     * Linearly interpolates between 2 float values using the formula f1+(a*(f2-f1)).
     * Any valid float values are acceptable, but values outside
     * intended range may produce strange results.
     *
     * @param f1 Start number
     * @param f2 End number
     * @param a Interpolation coefficient, intended values between 0.0 and 1.0
     * @return Result of the interpolation calculation
     */
    public static float lerp(float f1, float f2, float a)
    {
        return f1+(a*(f2-f1));
    }

    /**
     * Performs a lerp operation, then clamps the result between the min and max values given.
     * @param f1 Start number
     * @param f2 End number
     * @param a Interpolation coefficient
     * @param min Min value
     * @param max Max value
     * @return Result of the calculation
     */
    public static float clampedLerp(float f1, float f2, float a, float min, float max)
    {
        return clamp(min, max, lerp(f1, f2, a));
    }

    /**
     * Clamps a given value between a min and max value, e.g
     * (0, 5, 5.8) would yield 5, (0, 5, -1.3) would yield 0 and (0, 5, 3.2) would yield 3.2
     *
     * @param min Min value for the clamp operation
     * @param max Max value for the clamp operation
     * @param n Number to clamp
     * @return The clamped value
     */
    public static float clamp(float min, float max, float n)
    {
        n = Math.max(min, n);
        n = Math.min(max, n);

        return n;
    }

    /**
     *
     * @param in Number to loop
     * @param mod Modulo to loop within
     * @return Result
     */
    public static float modLoop(float in, float mod)
    {
        return in%mod;
    }
}
