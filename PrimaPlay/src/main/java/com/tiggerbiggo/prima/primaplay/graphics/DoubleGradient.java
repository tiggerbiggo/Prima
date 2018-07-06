package com.tiggerbiggo.prima.primaplay.graphics;

import com.tiggerbiggo.utils.calculation.Calculation;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;
import java.io.Serializable;

/**
 * Gradient that works with both X and Y components of a vector
 */
public class DoubleGradient extends Gradient implements Serializable {

  Color o, cx, cy;
  boolean loop;

  /**
   * @param o The color for the origin
   * @param cx The color for (1,0)
   * @param cy The color for (0,1)
   * @param loop Whether or not to loop colours
   * @throws IllegalArgumentException If any of the color arguments are null
   */
  public DoubleGradient(Color o, Color cx, Color cy, boolean loop) throws IllegalArgumentException {
    if (o == null || cx == null || cy == null) {
      throw new IllegalArgumentException("Colors cannot be null");
    }

    this.o = o;
    this.cx = cx;
    this.cy = cy;
    this.loop = loop;
  }

  /**
   * Default constructor, defaults to black white and blue.
   */
  public DoubleGradient() {
    this(Color.black, Color.white, Color.blue, false);
  }

  /**
   * @param a The vector to evaluate
   * @return The evaluated color
   * @see Gradient
   */
  @Override
  public Color evaluate(Vector2 a) {
    double x, y;
    x = a.X();
    y = a.Y();

    x = Calculation.modLoop(x, loop);
    y = Calculation.modLoop(y, loop);

    Color lx, ly;
    lx = ColorTools.colorLerp(o, cx, x);
    ly = ColorTools.colorLerp(o, cy, y);
    return ColorTools.colorLerp(lx, ly, Calculation.modLoop(x + y, loop));
  }
}
