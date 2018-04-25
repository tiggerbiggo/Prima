package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class MapGenNode implements NodeHasOutput {
  private double aX, aY, dx, dy;

  /**
   * Constructs a new MapGenNode with the given Vectors
   *
   * @param A The coordinate representing the bottom left of the map
   * @param B The coordinate representing the top right of the map
   */
  public MapGenNode(Vector2 A, Vector2 B) {
    set(A, B);
  }

  /**
   * Default constructor, defaults to (0,0), (1,1)
   */
  public MapGenNode(){
    this(new Vector2(0),new Vector2(1));
  }

  private void set(double aX, double aY, double bX, double bY) {
    this.aX = aX;
    this.aY = aY;

    dx = bX - aX;
    dy = bY - aY;

  }
  public void set(Vector2 A, Vector2 B){
    set(A.X(), A.Y(), B.X(), B.Y());
  }

  /**
   * Main output from this node, returns a coordinate corresponding to the
   * X, Y, Width and Height of the image, effectively returning the
   * appropriate point to make a coordinate map in the resulting image
   */
  private OutputLink<Vector2> vecOut = new OutputLink<Vector2>() {
    @Override
    public Vector2 get(RenderParams p) {
      return new Vector2(
          aX + (p.x() * (dx / p.width())),
          aY + (p.y() * (dy / p.height()))
      );
    }
  };

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink<?>[]{vecOut};
  }
}
