package com.tiggerbiggo.prima.processing.fragment.generate;

import com.tiggerbiggo.prima.core.Vector2;
import com.tiggerbiggo.prima.gui.ControlPane;
import com.tiggerbiggo.prima.gui.components.VectorPane;
import com.tiggerbiggo.prima.processing.fragment.Controllable;
import com.tiggerbiggo.prima.processing.fragment.Fragment;
import java.io.Serializable;

/**
 * Generates coordinate maps.
 */
public class MapGenFragment implements Fragment<Vector2>, Serializable, Controllable {

  private double aX, aY, dx, dy;

  /**
   * Constructs a new MapGenFragment with the given Vectors
   *
   * @param A The coordinate representing the bottom left of the map
   * @param B The coordinate representing the top right of the map
   */
  public MapGenFragment(Vector2 A, Vector2 B) {
    aX = A.X();
    aY = A.Y();

    dx = B.X() - aX;
    dy = B.Y() - aY;
  }

  /**
   * Constructs a new MapGenFragment with the given coordinates
   *
   * @param aX Bottom left X coordinate
   * @param aY Bottom left Y coordinate
   * @param bX Top right X coordinate
   * @param bY Top right Y coordinate
   */
  public MapGenFragment(double aX, double aY, double bX, double bY) {
    this(new Vector2(aX, aY), new Vector2(bX, bY));
  }

  /**
   * Shorthand constructor for when the X and Y coordinates of both A and B are the same, e.g (0,0)
   * and (1,1)
   *
   * @param A The XY components for vector A
   * @param B The XY components for vector B
   */
  public MapGenFragment(double A, double B) {
    this(A, A, B, B);
  }

  /**
   * The main calculation method. All processing for a given pixel should be done in this method.
   *
   * <p> When generating a map, the returned vector is determined by taking the image size and pixel
   * position and calculating the percentage distance the pixel being rendered lies across the
   * image. For example: <p>
   *
   * <b>Consider an image with width 100, height 100:</b> <ul> <li>Point (0,0) would be at 0% width
   * 0% height</li> <li>Point (50,50) would be at 50% width, 50% height</li> <li>Point (100,100)
   * would be at 100% width, 100% height</li> </ul> Next, the point the given pixel would lie is
   * calculated by multiplying the difference in the X and Y coordinates of the bottom left and top
   * right points by the above percentages and adding it to the bottom left coordinate. This results
   * in each rendered pixel being mapped to a coordinate on the number plane. This can then be used
   * by other fragments to generate transformed maps, or to map to coordinates on an image using
   * ImageConvertFragment.
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
    Vector2 v = new Vector2(
        aX + (x * (dx / w)),
        aY + (y * (dy / h))
    );
    return v;
  }

//    @Override
//    public Fragment<Vector2>[][] build(int xDim, int yDim) throws IllegalMapSizeException {
//        Fragment<Vector2>[][] map = MapGenerator.getFragMap(xDim, yDim, A, B);
//        return map;
//    }
//
//    @Override
//    public Fragment<Vector2>[][] getArray(int xDim, int yDim) throws IllegalMapSizeException {return null;}
//
//    @Override
//    public Fragment<Vector2> getNew(int i, int j) {return null;}

  @Override
  public ControlPane getControls(ControlPane p) {
    if (p == null) {
      return p;
    }

    VectorPane aPane, bPane;

//        aPane = new VectorPane(A, -1000, 1000, 0.1);
//        bPane = new VectorPane(B, -1000, 1000, 0.1);
//
//        p.add(aPane);
//        p.add(bPane);

    return p;
  }
}
