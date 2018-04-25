package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.RenderNode;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class BasicRenderNode extends RenderNode{
  private InputLink<Color[]> inputLink;

  @Override
  public BufferedImage[] Render(RenderParams p) {
    return new BufferedImage[0];
  }

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[0];
  }
}
