package com.tiggerbiggo.primaplay.node.implemented;

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
  public BufferedImage[] render(int width, int height, int n) {
    return Renderer.render(inputLink, width, height, n);
  }

  @Override
  public BufferedImage renderSingle(int width, int height) {
    return Renderer.renderSingle(inputLink, width, height);
  }
}
