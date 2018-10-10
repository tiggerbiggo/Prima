package com.tiggerbiggo.prima.primaplay.node.implemented.io.iterative;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorOutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;

public abstract class IterativeNode extends NodeInOut {

  VectorInputLink in;
  VectorOutputLink out;

  @TransferGrid
  private int iter;

  IterativeNode(int _iter) {
    this.iter = _iter;

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
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
