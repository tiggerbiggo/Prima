package com.tiggerbiggo.prima.processing.fragment;

/**
 * The base interface for all fragments
 *
 * @param <T> The object type the fragment should return
 */
public interface Fragment<T> {

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
  T get(int x, int y, int w, int h, int num);
}