package com.tiggerbiggo.prima.calculation;
import java.awt.*;

/**
 * Contains various static methods for calculating things like Hue, Saturation, Brightness, etc. using with the java.awt.Color class.
 */
public class ColorTools
{
    /**
     * Linearly interpolates between 2 colors. See lerp() for more information on how this functions.
     * @param c1 Start color
     * @param c2 End color
     * @param a Interpolation coefficient
     * @return new Color value generated from c1 and c2
     */
    public static Color colorLerp(Color c1, Color c2, double a)
    {
        return new Color(
                (int)Calculation.clampedLerp(c1.getRed(), c2.getRed(), a, 0, 255),
                (int)Calculation.clampedLerp(c1.getGreen(), c2.getGreen(), a, 0, 255),
                (int)Calculation.clampedLerp(c1.getBlue(), c2.getBlue(), a, 0, 255)
        );
    }

    public static Color colorAvg(Color ... colors) {
        int r, g, b;
        r = g = b = 0;

        for(Color c : colors) {
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
        }

        r /= colors.length;
        g /= colors.length;
        b /= colors.length;

        return new Color(r, g, b);
    }

    /**Calculates the brightness value of a given color
     * 
     * @param in The colour to calculate
     * @return The brightness value of the colour, between 0 and 1
     */
    public static double getBrightness(Color in)
    {
        return getMax(in) / 255.0f;
    }

    /**Calculates the saturation value of a given color
     *
     * @param in The colour to calculate
     * @return The saturation value of the colour, between 0 and 1
     */
    public static double getSaturation(Color in)
    {
        double min, max;
        min = getMin(in);
        max = getMax(in);

        if (max != 0)
            return (max - min) / max;
        else
            return 0;
    }

    /**Calculates the hue of a given color
     *
     * @param in The colour to calculate
     * @return The hue of the colour, between 0 and 1
     */
    public static double getHue(Color in)
    {
        if(getSaturation(in) == 0)
            return 0;

        double min, max, diff;
        min = getMin(in);
        max = getMax(in);
        diff = max-min;

        double redc = (max - in.getRed()) / diff;
        double greenc = (max - in.getGreen()) / diff;
        double bluec = (max - in.getBlue()) / diff;

        double hue;

        if (in.getRed() == max)
            hue = bluec - greenc;
        else if (in.getGreen() == max)
            hue = 2.0f + redc - bluec;
        else
            hue = 4.0f + greenc - redc;
        hue = hue / 6.0f;
        if (hue < 0)
            hue = hue + 1.0f;
        return hue;
    }

    /**Given a color, separates the RGB components and returns the largest value.
     * <p>
     * e.g getMax(new Color(50, 60, 70)) would return 70.
     * </p>
     *
     * @param in The color to calculate
     * @return The maximum RGB component
     */
    public static int getMax(Color in){
        return Math.max(in.getRed(), Math.max(in.getGreen(), in.getBlue()));
    }

    /**Given a color, separates the RGB components and returns the smallest value.
     * <p>
     * e.g getMax(new Color(50, 60, 70)) would return 50.
     * </p>
     *
     * @param in The color to calculate
     * @return The minimum RGB component
     */
    public static int getMin(Color in){
        return Math.min(in.getRed(), Math.min(in.getGreen(), in.getBlue()));
    }
}
