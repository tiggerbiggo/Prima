//Unfinished class, do not use.

package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorAction;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public class MultiColorEditNode extends NodeInOut {

  @TransferGrid
  private ColorAction action = ColorAction.ADD;

  private ColorArrayInputLink inA, inB;
  private ColorArrayOutputLink out;

  public MultiColorEditNode(){
    inA = new ColorArrayInputLink("A");
    inB = new ColorArrayInputLink("B");
    out = new ColorArrayOutputLink("Out") {
      @Override
      public Color[] get(RenderParams p) {
        Color[] A = inA.get(p);
        Color[] B = inB.get(p);
        Color[] toReturn = new Color[p.frameNum()];

        for(int i=0; i<p.frameNum(); i++){
          toReturn[i] = action.transform(A[i], B[i]);
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

    addInput(inA, inB);
    addOutput(out);
  }

  //@Override
  public String getName() {
    return "Multi Color Edit Node";
  }

  //@Override
  public String getDescription() {
    return "Allows transformation of Lists of colors.";
  }
}

