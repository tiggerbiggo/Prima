package com.tiggerbiggo.prima.graphics;

import com.tiggerbiggo.prima.core.Vector2;
import java.awt.Color;
import java.io.Serializable;

/**
 * Stores colors and calculates gradients
 */
public abstract class Gradient implements Serializable {

  /**
   * Abstract method for evaluating gradients
   *
   * @param a The vector to evaluate
   * @return The evaluated color
   */
  public abstract Color evaluate(Vector2 a);
}
