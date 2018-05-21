package com.tiggerbiggo.primaplay.node.old.implemented;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.old.core.Renderer;
import com.tiggerbiggo.primaplay.node.old.link.InputLink;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class RenderNode extends com.tiggerbiggo.primaplay.node.old.core.RenderNode {

  private InputLink<Color[]> inputLink = new InputLink<>();

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{inputLink};
  }

  @Override
  public BufferedImage[] render(RenderParams p) {
    return Renderer.render(inputLink, p);
  }
}
