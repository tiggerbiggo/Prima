package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.core.Vector2;
import java.awt.Color;

/**
 */
public class HueCycleGradient extends Gradient {

  /**
   * Abstract method for evaluating gradients
   *
   * @param a The vector to evaluate
   * @return The evaluated color
   */
  @Override
  public Color evaluate(Vector2 a) {
    Color c = Color.getHSBColor((a.fX() + a.fY()) / 2, 1, 1);
    return c;
  }
}
