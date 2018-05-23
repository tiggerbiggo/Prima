package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.RenderNode;
import com.tiggerbiggo.primaplay.node.core.Renderer;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.type.ColorArrayInputLink;
import java.awt.image.BufferedImage;

public class BasicRenderNode extends RenderNode {

  private ColorArrayInputLink inputLink = new ColorArrayInputLink();

  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{inputLink};
  }

  @Override
  public InputLink<?> getInput(int n) {
    return inputLink;
  }

  @Override
  public BufferedImage[] render(RenderParams p) {
    return Renderer.render(inputLink, p);
  }
}
