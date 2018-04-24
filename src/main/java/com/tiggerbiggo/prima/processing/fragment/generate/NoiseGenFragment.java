package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import java.io.Serializable;
import java.util.Random;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Generates random noise with an optional seed
 */
public class NoiseGenFragment implements Fragment<Vector2>, Serializable, Controllable {

  private long seed;
  private double mul;

  /**
   * Full constructor with set seed and multiplier
   *
   * @param seed The seed to use for the noise
   * @param mul The multiplier for the output noise
   */
  public NoiseGenFragment(long seed, double mul) {
    this.seed = seed;
    this.mul = mul;
  }

  /**
   * Shorthand constructor for a set multiplier but random seed
   *
   * @param mul The multiplier for the output noise
   */
  public NoiseGenFragment(double mul) {
    this(new Random().nextLong(), mul);
  }

  /**
   * Default constructor, has multiplier of 1 and random seed
   */
  public NoiseGenFragment() {
    this(1);
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
    Random r = new Random(seed + (x + (w * y)));
    return new Vector2(r.nextDouble() * mul, r.nextDouble() * mul);
  }

  @Override
  public ControlPane getControls(ControlPane p) {
    JSpinner s = new JSpinner(new SpinnerNumberModel(1, -1000, 1000, 0.1));
    s.addChangeListener(e -> mul = (double) s.getValue());
    p.add(s);
    return p;
  }
}