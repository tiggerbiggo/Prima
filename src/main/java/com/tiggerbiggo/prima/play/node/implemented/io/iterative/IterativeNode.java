package com.tiggerbiggo.prima.play.node.implemented.io.iterative;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.implemented.output.MapGenNode;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.defaults.MapGenDefaultLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class IterativeNode extends NodeInOut {

  VectorInputLink in;
  VectorOutputLink out;

  @TransferGrid
  private int iter;

  IterativeNode(int _iter) {
    this.iter = _iter;

    in = new MapGenDefaultLink(4, 4, -2, -2, "Input");
    addInput(in);

    out = new VectorOutputLink("Out") {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 z = initZ(p);
        Vector2 c = initC(p);
        for (int i = 0; i < iter; i++) {
          z = transform(z, c, i);
          if (escapeCheck(z, i)) {
            return onEscape(z, i);
          }
        }
        return onBound(z, iter);
      }

      @Override
      public void generateGLSLMethod(StringBuilder s) {
        //TODO
        throw new NotImplementedException();
      }

      @Override
      public String getMethodName() {
        //TODO
        throw new NotImplementedException();
      }
    };
    addOutput(out);
  }

  public abstract Vector2 initZ(RenderParams p);

  public abstract Vector2 initC(RenderParams p);

  public abstract Vector2 transform(Vector2 z, Vector2 c, int currentIteration);

  public abstract Vector2 onEscape(Vector2 in, int currentIteration);

  public abstract Vector2 onBound(Vector2 in, int currentIteration);

  public abstract boolean escapeCheck(Vector2 in, int currentIteration);
}
