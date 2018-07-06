package com.tiggerbiggo.primaplay.node.implemented.out;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class PixelMapNode implements INodeHasOutput {

  VectorOutputLink out;
  PixelMap map;

  public PixelMapNode(PixelMap _map) {
    this.map = _map;
    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        return map.get(p.x(), p.y());
      }
    };
    addOutput(out);
  }

  public PixelMapNode() {
    this(square(100, 100));
  }

  public static PixelMap square(int x, int y) {
    PixelMap map = new PixelMap(x, y);
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        double b = Math.max(i, j);
        map.set(i, j, new Vector2(b, b));
      }
    }
    return map;
  }

  @Override
  public String getName() {
    return "Pixel Map Node";
  }

  @Override
  public String getDescription() {
    return "Uses a Pixel Map to sample from, effectively a pre-set image.";
  }
}
