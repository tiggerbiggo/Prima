package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import java.awt.Color;

public class ColorConstructNode extends NodeInOut {

  private VectorInputLink posIn;
  private NumberArrayInputLink rLink, gLink, bLink;

  private ColorArrayOutputLink out;

  public ColorConstructNode(){
    posIn = new VectorInputLink();
    rLink = new NumberArrayInputLink();
    gLink = new NumberArrayInputLink();
    bLink = new NumberArrayInputLink();

    addInput(posIn, rLink, gLink, bLink);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        Color[] toReturn = new Color[p.frameNum()];

        Vector2 pos = posIn.get(p);

        Double[] r = rLink.get(p);
        Double[] g = gLink.get(p);
        Double[] b = bLink.get(p);

        for (int i = 0; i < p.frameNum(); i++) {
          r[i] = Calculation.clamp(0, 1, r[i]);
          g[i] = Calculation.clamp(0, 1, g[i]);
          b[i] = Calculation.clamp(0, 1, b[i]);
          toReturn[i] = new Color((float)((double)r[i]),(float)((double)g[i]),(float)((double)b[i]));
        }

        return toReturn;
      }
    };

    addOutput(out);
  }


  @Override
  public String getName() {
    return "Color Construct Node";
  }

  @Override
  public String getDescription() {
    return "Constructs RGB colours from input signals.";
  }
}
