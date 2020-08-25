package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.function.Function;

public class DualAnimateNode extends NodeInOut {

  VectorInputLink inA, inB;
  VectorArrayOutputLink out;

  Function<Double, Double> func;

  public DualAnimateNode(Function<Double, Double> _func) {
    this.func = _func;

    inA = new VectorInputLink("A");
    inB = new VectorInputLink("B");

    addInput(inA, inB);

    out = new VectorArrayOutputLink("Out") {
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
