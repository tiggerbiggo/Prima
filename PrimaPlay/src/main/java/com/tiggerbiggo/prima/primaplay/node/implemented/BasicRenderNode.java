package com.tiggerbiggo.prima.primaplay.node.implemented;

import com.tiggerbiggo.prima.primaplay.core.NewRenderer;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.prima.primaplay.core.Renderer;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import java.awt.image.BufferedImage;
import java.util.concurrent.Future;

public class BasicRenderNode extends NodeHasInput{

  private ColorArrayInputLink inputLink;

  public BasicRenderNode(){
    inputLink = new ColorArrayInputLink();
    addInput(inputLink);
  }

  public Future<BufferedImage[]> renderAsync(int width, int height, int n){
    return NewRenderer.renderAsync(inputLink, width, height, n);
  }

  public BufferedImage[] render(int width, int height, int n) {
    return Renderer.render(inputLink, width, height, n);
  }

  public BufferedImage renderSingle(int width, int height) {
    return Renderer.renderSingle(inputLink, width, height);
  }

  @Override
  public String getName() {
    return "Basic Render Node";
  }

  @Override
  public String getDescription() {
    return "Simple renderer. Input color array, no link outputs, only render output.";
  }
}
