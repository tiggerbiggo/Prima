package com.tiggerbiggo.prima.primaplay.node.implemented;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;

public class MapGenNode implements INodeHasOutput {

  @TransferGrid
  private Vector2 A, B;

  VectorOutputLink mapOut;

  public MapGenNode(Vector2 _A, Vector2 _B) {
    A = _A;
    B = _B;

    mapOut = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        double x = p.x();
        double y = p.y();
        double width = p.width();
        double height = p.height();

        Vector2 percent = new Vector2(
            x / width,
            y / height
        );
        return A.lerpVector(B, percent);
      }
    };
    addOutput(mapOut);
  }

  public MapGenNode() {
    this(Vector2.ZERO, Vector2.ONE);
  }

  @Override
  public String getName() {
    return "Map Gen Node";
  }

  @Override
  public String getDescription() {
    return "Generates coordinate maps";
  }
}

/*

  @TransferGrid
  private double aX, aY, dx, dy;
  /**
   * Main output from this node, returns a coordinate corresponding to the X, Y, Width and Height of
   * the image, effectively returning the appropriate point to make a coordinate map inputLink the
   * resulting image
   *
  private VectorOutputLink vecOut;

  /**
   * Constructs a new MapGenNode with the given Vectors
   *
   * @param A The coordinate representing the bottom left of the map
   * @param B The coordinate representing the top right of the map
   *
  public MapGenNode(Vector2 A, Vector2 B) {
    set(A, B);
    vecOut = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return new Vector2(
            aX + (p.x() * (dx / p.width())),
            aY + (p.y() * (dy / p.height()))
        );
      }
    };
    addOutput(vecOut);
  }

  /**
   * Default constructor, defaults to (0,0), (1,1)
   *
  public MapGenNode() {
    this(new Vector2(0), new Vector2(1));
  }

  private void set(double aX, double aY, double bX, double bY) {
    this.aX = aX;
    this.aY = aY;

    dx = bX - aX;
    dy = bY - aY;
  }

  public void set(Vector2 A, Vector2 B) {
    set(A.X(), A.Y(), B.X(), B.Y());
  }

  @Override
  public String getName() {
    return "Map Gen Node";
  }

  @Override
  public String getDescription() {
    return "Generates coordinate maps";
  }
}
*//*/*/