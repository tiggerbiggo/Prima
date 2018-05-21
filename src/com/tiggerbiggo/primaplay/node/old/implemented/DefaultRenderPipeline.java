package com.tiggerbiggo.primaplay.node.old.implemented;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.old.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.old.link.InputLink;

public class DefaultRenderPipeline implements NodeHasInput {

  InputLink<Vector2> in = new InputLink<>();

  RenderNode render;
  AnimationNode anim;
  GradientNode gradientNode;


  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[]{in};
  }
}
