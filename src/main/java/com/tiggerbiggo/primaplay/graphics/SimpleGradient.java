package com.tiggerbiggo.primaplay.graphics;

import com.tiggerbiggo.primaplay.calculation.Calculation;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import java.awt.Color;
import java.io.Serializable;

/**
 * Simple gradient for 2 colors
 */
public class SimpleGradient extends Gradient implements Serializable {

  Color c1, c2;
  boolean loop;

  /**
   * @param c1 The first color
   * @param c2 The second color
   * @param loop Whether or not to loop
   * @throws IllegalArgumentException if c1 or c2 are null
   */
  public SimpleGradient(Color c1, Color c2, boolean loop) throws IllegalArgumentException {
    this.c1 = c1;
    this.c2 = c2;
    this.loop = loop;
  }

  /**
   * Default constructor, defaults to black, white, and no loop
   */
  public SimpleGradient() {
    this(Color.black, Color.white, false);
  }

  /**
   * Evaluates a vector to a color
   *
   * @param v The vector to evaluate
   * @return The evaluated color
   */
  @Override
  public Color evaluate(Vector2 v) {
    double a = v.X() + v.Y();
    a = Calculation.modLoop(a, loop);
    return ColorTools.colorLerp(c1, c2, a);
  }
}
