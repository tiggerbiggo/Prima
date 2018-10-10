package com.tiggerbiggo.prima.primaplay.node.implemented.io;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.graphics.Gradient;
import com.tiggerbiggo.prima.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.prima.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.prima.primaplay.node.link.type.VectorArrayInputLink;
import com.tiggerbiggo.utils.calculation.Vector2;
import java.awt.Color;

public class GradientNode extends NodeInOut {
  VectorArrayInputLink inputLink;
  ColorArrayOutputLink out;


  Gradient g;

  public GradientNode(Gradient _g) {
    g = _g;

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

  public GradientNode(){
    this(new HueCycleGradient());
  }

  @Override
  public String getName() {
    return "Gradient Node";
  }

  @Override
  public String getDescription() {
    return "Takes an array of Vectors and turns them into an array of Colors.";
  }
}
