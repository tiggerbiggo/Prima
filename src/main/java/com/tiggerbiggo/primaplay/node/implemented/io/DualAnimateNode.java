package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import java.util.function.Function;

public class DualAnimateNode extends NodeInOut {

  VectorInputLink inA, inB;
  VectorArrayOutputLink out;
  Function<Double, Double> func;

  public DualAnimateNode(Function<Double, Double> func) {
    this.func = func;

    inA = new VectorInputLink();
    inB = new VectorInputLink();

    addInput(inA, inB);

    out = new VectorArrayOutputLink() {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2 A, B;
        A = inA.get(p);
        B = inB.get(p);
        Vector2[] toReturn = new Vector2[p.n()];
        for (int i = 0; i < p.n(); i++) {
          toReturn[i] = Vector2.lerpVector(A, B, func.apply((double) i / p.n()));
        }
        return toReturn;
      }
    };
    addOutput(out);
  }

  public DualAnimateNode() {
    this(d -> (Math.sin(d * Math.PI * 2) + 1) / 2);
  }
}
