//Unfinished class, do not use.

package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorAction;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorInputLink;
import com.tiggerbiggo.prima.play.node.link.type.ColorOutputLink;
import java.awt.Color;

public class ColorEditNode extends NodeInOut {

  @TransferGrid
  private ColorAction action = ColorAction.ADD;

  private ColorInputLink inA, inB;
  private ColorOutputLink out;

  public ColorEditNode(){
    inA = new ColorInputLink();
    inB = new ColorInputLink();
    out = new ColorOutputLink() {
      @Override
      public Color get(RenderParams p) {
        return action.transform(inA.get(p), inB.get(p));
      }
    };

    addInput(inA, inB);
    addOutput(out);
  }

  //@Override
  public String getName() {
    return "Color Edit Node";
  }

  //@Override
  public String getDescription() {
    return "Allows transformation of colors.";
  }
}

