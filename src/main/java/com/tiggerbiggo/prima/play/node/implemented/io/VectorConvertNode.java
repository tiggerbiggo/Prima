package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayInputLink;

public class VectorConvertNode extends NodeInOut {

  private VectorArrayInputLink in;
  private NumberArrayOutputLink out;

  public VectorConvertNode() {
    in = new VectorArrayInputLink();
    addInput(in);

    out = new NumberArrayOutputLink() {
      @Override
      public Double[] get(RenderParams p) {
        Vector2[] arr = in.get(p);
        Double[] toReturn = new Double[p.frameNum()];

        for(int i=0; i<p.frameNum(); i++){
          toReturn[i] = arr[i].xy();
        }
        return toReturn;
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Vector Convert Node";
  }

  @Override
  public String getDescription() {
    return "Direct conversion of Vector > Number";
  }
}
