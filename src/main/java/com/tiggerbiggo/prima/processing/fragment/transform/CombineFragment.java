package com.tiggerbiggo.prima.processing.fragment.transform;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import java.io.Serializable;

/**
 * Combines 2 fragments to produce an output
 */
public class CombineFragment implements Fragment<Vector2>, Serializable {

  Fragment<Vector2> A, B;
  CombineType type;

  public CombineFragment(Fragment<Vector2> A, Fragment<Vector2> B, CombineType type) {
    this.A = A;
    this.B = B;
    this.type = type;
  }

  /**
   * The main calculation method. All processing for a given pixel should be done in this method.
   *
   * @param x The X position of the pixel being rendered
   * @param y The Y position of the pixel being rendered
   * @param w The width of the image
   * @param h The height of the image
   * @param num The number of frames in the animation
   * @return The output of the fragment
   */
  @Override
  public Vector2 get(int x, int y, int w, int h, int num) {
    return type.combine(A, B, x, y, w, h, num);
  }
}
