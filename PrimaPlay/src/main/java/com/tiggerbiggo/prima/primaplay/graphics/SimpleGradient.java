package com.tiggerbiggo.prima.primaplay.graphics;

import com.tiggerbiggo.utils.calculation.Calculation;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;

/**
 * Simple gradient for 2 colors
 */
public class SimpleGradient{
  public static Color evaluate(Vector2 v, Color A, Color B, boolean loop) {
    double a = v.xy();
    a = Calculation.modLoop(a, loop);
    return ColorTools.colorLerp(A, B, a);
  }
}
