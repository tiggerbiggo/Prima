package com.tiggerbiggo.prima.play.node.implemented.io;

import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.NumberArrayInputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.Color;

public class ColorConstructNode extends NodeInOut {

  private VectorInputLink posIn;
  private NumberArrayInputLink rLink, gLink, bLink;

  private ColorArrayOutputLink out;

  public ColorConstructNode(){
    rLink = new NumberArrayInputLink("R");
    gLink = new NumberArrayInputLink("G");
    bLink = new NumberArrayInputLink("B");

    addInput(rLink, gLink, bLink);

    out = new ColorArrayOutputLink("Out") {
      @Override
      public Color[] get(RenderParams p) {
        Color[] toReturn = new Color[p.frameNum()];

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
    return "Color Construct Node";
  }

  @Override
  public String getDescription() {
    return "Constructs RGB colours from input signals.";
  }
}
