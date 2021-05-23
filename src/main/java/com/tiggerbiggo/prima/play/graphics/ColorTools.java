package com.tiggerbiggo.prima.play.graphics;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import java.awt.Color;
import java.util.Random;

/**
 * Contains various static methods for calculating things like Hue, Saturation, Brightness, etc.
 * using with the java.awt.Color class.
 */
public class ColorTools {

  /**
   * Linearly interpolates between 2 colors. See lerp() for more information on how this functions.
   *
   * @param c1 Start color
   * @param c2 End color
   * @param a Interpolation coefficient
   * @return new Color value generated from c1 and c2
   */
  public static Color colorLerp(Color c1, Color c2, double a) {
    return new Color(
        (int) Calculation.clampedLerp(c1.getRed(), c2.getRed(), a, 0, 255),
        (int) Calculation.clampedLerp(c1.getGreen(), c2.getGreen(), a, 0, 255),
        (int) Calculation.clampedLerp(c1.getBlue(), c2.getBlue(), a, 0, 255)
    );
  }

  /**
   * Takes a mean average of the colors passed.
   *
   * <div> (C1 + C2 + C3 + ... + Cn) / n </div>
   *
   * @param colors A vararg for the colors to be averaged.
   * @return A single color which is the average of the colors passed.
   */
  public static Color colorAvg(Color... colors) {
    int r, g, b;
    r = g = b = 0;

    for (Color c : colors) {
      r += c.getRed();
      g += c.getGreen();
      b += c.getBlue();
    }

    r /= colors.length;
    g /= colors.length;
    b /= colors.length;

    return new Color(r, g, b);
  }

  /**
   * Calculates the brightness value of a given color
   *
   * @param in The colour to calculate
   * @return The brightness value of the colour, between 0 and 1
   */
  public static float getBrightness(Color in) {
    return getMax(in) / 255.0f;
  }

  /**
   * Calculates the saturation value of a given color
   *
   * @param in The colour to calculate
   * @return The saturation value of the colour, between 0 and 1
   */
  public static float getSaturation(Color in) {
    float min, max;
    min = getMin(in);
    max = getMax(in);

    if (max != 0) {
      return (max - min) / max;
    } else {
      return 0;
    }
  }

  /**
   * Calculates the hue of a given color
   *
   * @param in The colour to calculate
   * @return The hue of the colour, between 0 and 1
   */
  public static float getHue(Color in) {
    if (getSaturation(in) == 0) {
      return 0;
    }

    float min, max, diff;
    min = getMin(in);
    max = getMax(in);
    diff = max - min;

    float redc = (max - in.getRed()) / diff;
    float greenc = (max - in.getGreen()) / diff;
    float bluec = (max - in.getBlue()) / diff;

    float hue;

    if (in.getRed() == max) {
      hue = bluec - greenc;
    } else if (in.getGreen() == max) {
      hue = 2.0f + redc - bluec;
    } else {
      hue = 4.0f + greenc - redc;
    }
    hue = hue / 6.0f;
    if (hue < 0) {
      hue = hue + 1.0f;
    }
    return hue;
  }

  /**
   * Given a color, separates the RGB components and returns the largest value. <p> e.g getMax(new
   * Color(50, 60, 70)) would return 70. </p>
   *
   * @param in The color to calculate
   * @return The maximum RGB component
   */
  public static int getMax(Color in) {
    return Math.max(in.getRed(), Math.max(in.getGreen(), in.getBlue()));
  }

  /**
   * Given a color, separates the RGB components and returns the smallest value. <p> e.g getMax(new
   * Color(50, 60, 70)) would return 50. </p>
   *
   * @param in The color to calculate
   * @return The minimum RGB component
   */
  public static int getMin(Color in) {
    return Math.min(in.getRed(), Math.min(in.getGreen(), in.getBlue()));
  }

  /**Generates an array of col with length n
   *
   * @param n
   * @return 
   * @throws ArrayIndexOutOfBoundsException if n < 0
   */
  public static Color[] colorArray(int n, Color col) throws ArrayIndexOutOfBoundsException{
    if(n < 0){
      throw new ArrayIndexOutOfBoundsException("Error in ColorTools.colorArray: Number given was < 0, n: " + n);
    }
    Color[] toReturn = new Color[n];
    for (int i=0 ;i<n; i++){
      toReturn[i] = col;
    }
    return toReturn;
  }

  public static Color[] colorArray(int n) throws ArrayIndexOutOfBoundsException{
    return colorArray(n, Color.BLACK);
  }

  public static Color fromFXColor(javafx.scene.paint.Color fxColor){
    return new Color(
        (int)(fxColor.getRed() * 255),
        (int)(fxColor.getGreen() * 255),
        (int)(fxColor.getBlue() * 255)
    );
  }

  public static javafx.scene.paint.Color toFXColor(Color c){
    return new javafx.scene.paint.Color(
        c.getRed() / 255d,
        c.getGreen() / 255d,
        c.getBlue() / 255d,
        1
    );
  }

  /**Avoids exceptions, makes a color that is clamped properly.
   *
   * @param r
   * @param g
   * @param b
   * @return
   */
  public static Color constrain(int r, int g, int b){
    return new Color(
        Math.min(255, Math.max(0, r)),
        Math.min(255, Math.max(0, g)),
        Math.min(255, Math.max(0, b))
    );
  }
  /**Avoids exceptions, makes a color that is clamped properly from float values (0-1 range).
   *
   * @param r
   * @param g
   * @param b
   * @return
   */
  public static Color constrain(float r, float g, float b){
    return new Color(
        Math.min(1, Math.max(0, r)),
        Math.min(1, Math.max(0, g)),
        Math.min(1, Math.max(0, b))
    );
  }

  public static Color add(Color c1, Color c2) {
    return constrain(
        c1.getRed() + c2.getRed(),
        c1.getGreen() + c2.getGreen(),
        c1.getBlue() + c2.getBlue()
    );
  }

  public static Color subtract(Color c1, Color c2) {
    return constrain(
        c1.getRed() - c2.getRed(),
        c1.getGreen() - c2.getGreen(),
        c1.getBlue() - c2.getBlue()
    );
  }

  public static Color multiply(Color c1, Color c2){
    return constrain(
        c1.getRed() * c2.getRed(),
        c1.getGreen() * c2.getGreen(),
        c1.getBlue() * c2.getBlue()
    );
  }
  public static Color fMultiply(Color c1, Color c2){
    return constrain(
            fGetRed(c1) * fGetRed(c2),
            fGetGreen(c1) * fGetGreen(c2),
            fGetBlue(c1) * fGetBlue(c2)
    );
  }

  public static Color fMultiply(Color c1, int i){
    return fMultiply(c1, constrain(i,i,i));
  }

  public static Color divide(Color c1, Color c2){
    if(c2.getRed() == 0 || c2.getGreen() == 0 || c2.getBlue() == 0){
      return c1;
    }
    return constrain(
        c1.getRed() / c2.getRed(),
        c1.getGreen() / c2.getGreen(),
        c1.getBlue() / c2.getBlue()
    );
  }

  public static Color fDivide(Color c1, Color c2){
    if(c2.getRed() == 0 || c2.getGreen() == 0 || c2.getBlue() == 0){
      return c1;
    }
    return constrain(
            fGetRed(c1) / fGetRed(c2),
            fGetGreen(c1) / fGetGreen(c2),
            fGetBlue(c1) / fGetBlue(c2)
    );
  }

  public static Color invert(Color c){
    return constrain(
        255 - c.getRed(),
        255 - c.getGreen(),
        255 - c.getBlue()
    );
  }

  public static int absoluteDifference(Color A, Color B){
    return
            Math.abs(A.getRed() - B.getRed()) +
            Math.abs(A.getGreen() - B.getGreen()) +
            Math.abs(A.getBlue() - B.getBlue());
  }

  public static String colorAsVec4(Color c){
    float r = c.getRed()/255f;
    float g = c.getGreen()/255f;
    float b = c.getBlue()/255f;
    return "vec4("+r+","+g+","+b+",0)";
  }

  public static Color randomColor(Random r){
    return new Color(
            r.nextInt(255),
            r.nextInt(255),
            r.nextInt(255)
    );
  }

  public static Color fromHexString(String s) {
    s = s.trim().toLowerCase();
    if(s.length() != 6) return null;
    try{
      int r = Integer.parseInt(s.substring(0,2), 16);
      int g = Integer.parseInt(s.substring(2,4), 16);
      int b = Integer.parseInt(s.substring(4,6), 16);
      return constrain(r, g, b);
    }
    catch(NumberFormatException ex){
      return null;
    }
  }

  public static float fGetRed(Color c){
    return c.getRed() / 255f;
  }
  public static float fGetGreen(Color c){
    return c.getGreen() / 255f;
  }
  public static float fGetBlue(Color c){
    return c.getBlue() / 255f;
  }
}


