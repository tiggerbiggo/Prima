package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import java.awt.Color;

public class ColorMaskNode extends NodeInOut{

  @TransferGrid
  private double threshold = 1;

  @TransferGrid
  private boolean clamp = false;

  private ColorArrayInputLink colorA, colorB;
  private NumberArrayInputLink maskIn;

  private ColorArrayOutputLink out;

  public ColorMaskNode(){
    colorA = new ColorArrayInputLink();
    colorB = new ColorArrayInputLink();
    maskIn = new NumberArrayInputLink();
    addInput(colorA, colorB, maskIn);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        //for each frame:
        //  A <-> B using threshold and clamp
        Color[] A = colorA.get(p);
        Color[] B = colorB.get(p);
        Double[] M = maskIn.get(p);

        Color[] toReturn = new Color[p.frameNum()];

        for(int i=0; i<p.frameNum(); i++){
          //0 is fully A
          //1 is fully B
          //if !clamp then loop, else minmax
          Color tmp = ColorTools.colorLerp(A[i], B[i], Calculation.clamp(0,1,M[i]));
          toReturn[i] = tmp;
        }

        return toReturn;
      }
    };
    addOutput(out);
  }

  @Override
  public String getName() {
    return "Color Mask Node";
  }

  @Override
  public String getDescription() {
    return "";
  }
}
