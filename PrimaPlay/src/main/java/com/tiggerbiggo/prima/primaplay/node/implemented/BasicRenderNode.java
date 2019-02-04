package com.tiggerbiggo.prima.primaplay.node.implemented;

import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.core.render.RenderCallback;
import com.tiggerbiggo.prima.primaplay.core.render.RenderTask;
import com.tiggerbiggo.prima.primaplay.core.render.Renderer;
import com.tiggerbiggo.prima.primaplay.graphics.ColorTools;
import com.tiggerbiggo.prima.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.link.type.ColorArrayInputLink;
import java.awt.Color;

public class BasicRenderNode extends NodeHasInput{

  private ColorArrayInputLink inputLink;

  public BasicRenderNode(){
    inputLink = new ColorArrayInputLink() {
      @Override
      public Color[] defaultValue(RenderParams p) {
        return ColorTools.blankArray(p.frameNum());
      }
    };
    addInput(inputLink);
  }

  public RenderTask renderAsync(int width, int height, int frameNum, String desc, RenderCallback ... callbacks){
    RenderTask task = new RenderTask(width, height, frameNum, inputLink, desc, callbacks);
    Renderer.getInstance().queue(task);
    return task;
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
