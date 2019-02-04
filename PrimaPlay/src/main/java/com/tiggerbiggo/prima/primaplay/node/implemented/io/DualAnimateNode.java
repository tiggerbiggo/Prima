package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.util.function.Function;

public class DualAnimateNode extends NodeInOut {

  VectorInputLink inA, inB;
  VectorArrayOutputLink out;

  Function<Double, Double> func;

  public DualAnimateNode(Function<Double, Double> _func) {
    this.func = _func;

    inA = new VectorInputLink();
    inB = new VectorInputLink();

    addInput(inA, inB);

    out = new VectorArrayOutputLink() {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2 A, B;
        A = inA.get(p);
        B = inB.get(p);
        Vector2[] toReturn = new Vector2[p.frameNum()];
        for (int i = 0; i < p.frameNum(); i++) {
          toReturn[i] = A.lerp(B, func.apply((double) i / p.frameNum()));
        }
        return toReturn;
      }
    };
    addOutput(out);
  }

  public DualAnimateNode() {
    this(d -> (Math.sin(d * Math.PI * 2) + 1) / 2);
  }

  @Override
  public String getName() {
    return "Dual Animate Node";
  }

  @Override
  public String getDescription() {
    return "Interpolates between 2 input vectors to create an animated output.";
  }
}
