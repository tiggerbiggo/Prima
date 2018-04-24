package com.tiggerbiggo.prima.processing.fragment.render;

import com.tiggerbiggo.prima.calculation.Calculation;
import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import java.util.function.Function;

/**
 * Takes a static Vector and animates it
 */
public class AnimationFragment implements Fragment<Vector2[]> {

  private Fragment<Vector2> in;
  private Function<Double, Vector2> func;

  /**
   * Constructs a new AnimationFragment
   *
   * @param in The vector fragment to animate
   * @param func The Function used to animate the fragment
   */
  public AnimationFragment(Fragment<Vector2> in, Function<Double, Vector2> func) {
    this.in = in;
    this.func = func;
  }

  /**
   * The main calculation method. All processing for a given pixel should be done in this method.
   *
   * For each frame (given by the num parameter), this method does the following: <ol>
   * <li>Calculates the percentage of the current iteration (current/total)</li> <li>Passes this
   * value (which is between 0.0 and 1.0) to the Function given at construction</li> <li>Stores the
   * resulting Vector in an array at index corresponding to the current frame</li> </ol>
   *
   * @param x The X position of the pixel being rendered
   * @param y The Y position of the pixel being rendered
   * @param w The width of the image
   * @param h The height of the image
   * @param num The number of frames in the animation
   * @return The output of the fragment
   */
  @Override
  public Vector2[] get(int x, int y, int w, int h, int num) {
    Vector2 start = in.get(x, y, w, h, num);

    Vector2[] toReturn = new Vector2[num];
    for (int i = 0; i < num; i++) {
      double percent = (double) i / num;
      toReturn[i] = Vector2.add(func.apply(percent), start);
    }

    return toReturn;
  }

  public static final Function<Double, Vector2> SIMPLE = Vector2::new;
  public static final Function<Double, Vector2> SINSIN = (i) -> new Vector2(
      Math.sin(i * Math.PI * 2) * (1 / Math.PI));
  public static final Function<Double, Vector2> REVERSE = (i) -> new Vector2(
      Calculation.modLoop(i, true));
  public static final Function<Double, Vector2> STILL = (i) -> new Vector2(0);
}
