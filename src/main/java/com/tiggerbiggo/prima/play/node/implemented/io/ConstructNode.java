package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ConstructNode extends NodeInOut {

  NumberArrayInputLink A, B;

  VectorArrayOutputLink out;

  public ConstructNode() {
    A = new NumberArrayInputLink("A");
    B = new NumberArrayInputLink("B");
    addInput(A, B);

    out = new VectorArrayOutputLink("Out") {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2[] toReturn = new Vector2[p.frameNum()];
        Double[] dA = A.get(p);
        Double[] dB = B.get(p);

        for (int i = 0; i < p.frameNum(); i++) {
          toReturn[i] = new Vector2(dA[i], dB[i]);
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

  @Override
  public String getName() {
    return "Construct Node";
  }

  @Override
  public String getDescription() {
    return "Constructs a vector from 2 numbers";
  }
}
