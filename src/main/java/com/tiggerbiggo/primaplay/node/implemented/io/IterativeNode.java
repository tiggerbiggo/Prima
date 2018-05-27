package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.ComplexNumber;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;

public class IterativeNode extends NodeInOut {

  VectorInputLink in;
  VectorOutputLink out;
  int iter;

  public IterativeNode(int iter) {
    this.iter = iter;

    in = new VectorInputLink();
    addInput(in);

    out = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 current = in.get(p);
        for (int i = 0; i < iter; i++) {
          current = transform(current);
          if (hasEscaped(current)) {
            break;
          }
        }
        return current;
      }
    };
  }

  public Vector2 transform(Vector2 in) {
    ComplexNumber n = in.asComplex();

    return ComplexNumber.multiply(n, n).asVector();
  }

  public boolean hasEscaped(Vector2 in) {
    return false;
  }
}
