package com.tiggerbiggo.primaplay.node.old.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.graphics.Gradient;
import com.tiggerbiggo.primaplay.node.old.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.old.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.old.link.InputLink;
import com.tiggerbiggo.primaplay.node.old.link.OutputLink;
import java.awt.Color;

public class GradientNode implements NodeHasInput, NodeHasOutput {

  InputLink<Vector2[]> inputLink = new InputLink<>();
  OutputLink<Color[]> out;
  Gradient g;

  public GradientNode(Gradient in) {
    out = new OutputLink<Color[]>() {
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
  }

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{inputLink};
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out};
  }
}
