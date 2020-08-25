package com.tiggerbiggo.prima.play.node.implemented;

import ch.hephaistos.utilities.loki.util.interfaces.ChangeListener;
import com.tiggerbiggo.prima.play.core.render.RenderCallback;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.core.render.RenderTask;
import com.tiggerbiggo.prima.play.core.render.Renderer;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.core.NodeHasInput;
import com.tiggerbiggo.prima.play.node.link.type.ColorArrayInputLink;
import com.tiggerbiggo.prima.view.sample.components.AnimatedImageView;
import java.awt.Color;
import javafx.scene.Node;

public class BasicRenderNode extends NodeHasInput{

  private ColorArrayInputLink inputLink;

  public BasicRenderNode(){
    inputLink = new ColorArrayInputLink("To Renderer") {
      @Override
      public Color[] defaultValue(RenderParams p) {
        return ColorTools.colorArray(p.frameNum());
      }
    };
    addInput(inputLink);
  }

  public RenderTask renderAsync(int width, int height, int frameNum, String desc, RenderCallback ... callbacks){
    return Renderer.queueToDefaultRenderer(width, height, frameNum, inputLink, callbacks);
  }

  @Override
  public String getName() {
    return "OUTPUT";
  }

  @Override
  public String getDescription() {
    return "Simple renderer. Input color array, no link outputs, only render output.";
  }

  @Override
  public Node getFXNode(ChangeListener listener) {

    return new AnimatedImageView();
  }
}
