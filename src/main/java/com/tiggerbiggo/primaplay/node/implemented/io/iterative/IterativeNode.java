package com.tiggerbiggo.primaplay.node.implemented.io.iterative;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public abstract class IterativeNode extends NodeInOut {

  VectorInputLink in;
  VectorOutputLink out;
  int iter;

  public IterativeNode(int iter, Vector2 _c) {
    this.iter = iter;

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 z = initZ(p);
        Vector2 c = initC(p);
        for (int i = 0; i < iter; i++) {
          z = transform(z, c);
          if (escapeCheck(z)) {
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

  public abstract Vector2 transform(Vector2 z, Vector2 c);

  public abstract Vector2 onEscape(Vector2 in, int currentIteration);

  public abstract Vector2 onBound(Vector2 in, int currentIteration);

  public abstract boolean escapeCheck(Vector2 in);
}
