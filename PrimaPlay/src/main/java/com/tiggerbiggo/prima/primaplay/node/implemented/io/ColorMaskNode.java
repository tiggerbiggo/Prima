package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;

public class ColorMaskNode extends NodeInOut{

  @TransferGrid
  private double threshold = 1;

  @TransferGrid
  private boolean clamp = false;

  private ColorArrayInputLink colorIn;
  private VectorInputLink maskIn;

  private ColorArrayOutputLink out;

  public ColorMaskNode(){
    colorIn = new ColorArrayInputLink();
    maskIn = new VectorInputLink();
    addInput(colorIn, maskIn);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        Color[] toReturn = colorIn.get(p);
        Vector2 mask = maskIn.get(p);

        double multiplier = mask.xy() * threshold;
        if(clamp){
          multiplier = Math.min(1d, Math.max(0d, multiplier));
        }

        for(int i=0; i<toReturn.length; i++){
          //toReturn[i] = ColorTools.multiply(toReturn[i], multiplier); TODO: BORKED
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
