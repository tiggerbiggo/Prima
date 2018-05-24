package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.graphics.Gradient;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayInputLink;
import java.awt.Color;

public class GradientNode implements NodeHasInput, NodeHasOutput {

  VectorArrayInputLink inputLink = new VectorArrayInputLink();
  ColorArrayOutputLink out;
  Gradient g;

  public GradientNode(Gradient in) {
    g = in;
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
  }

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{inputLink};
  }

  @Override
  public InputLink<?> getInput(int n) {
    return inputLink;
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[]{out};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return out;
  }
}
