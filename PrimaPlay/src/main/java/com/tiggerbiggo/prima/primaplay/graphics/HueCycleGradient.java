package com.tiggerbiggo.prima.primaplay.graphics;

import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;

/**
 */
public class HueCycleGradient{
  public static Color evaluate(Vector2 a) {
    Color c = Color.getHSBColor((a.fX() + a.fY()) / 2, 1, 1);
    return c;
  }
}
