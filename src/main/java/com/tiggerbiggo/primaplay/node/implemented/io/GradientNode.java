package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.graphics.Gradient;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayInputLink;
import java.awt.Color;

public class GradientNode extends NodeInOut {

  VectorArrayInputLink inputLink;
  ColorArrayOutputLink out;
  Gradient g;

  public GradientNode(Gradient in) {
    g = in;

    inputLink = new VectorArrayInputLink();
    addInput(inputLink);

    out = new ColorArrayOutputLink() {
      @Override
      public Color[] get(RenderParams p) {
        Vector2[] in = inputLink.get(p);
        Color[] toReturn = new Color[in.length];
        for (int i = 0; i < in.length; i++) {
          toReturn[i] = g.evaluate(in[i]);
        }
        return toReturn;
      }
    };
    addOutput(out);
  }
}
