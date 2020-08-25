package com.tiggerbiggo.prima.play.node.implemented.output;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PixelMapNode extends NodeHasOutput {

  VectorOutputLink out;
  PixelMap map;

  public PixelMapNode(PixelMap _map) {
    this.map = _map;
    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        return map.get(p.x(), p.y());
      }
      @Override
      public void generateGLSLMethod(StringBuilder s) {
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        throw new NotImplementedException();
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
    return "Uses a Pixel Map to com.tiggerbiggo.prima.view.sample from, effectively a pre-set image.";
  }
}
