package com.tiggerbiggo.primaplay.node.old.implemented;

import com.tiggerbiggo.primaplay.node.old.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.old.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.old.link.InputLink;
import com.tiggerbiggo.primaplay.node.old.link.OutputLink;

public class AnimationNode implements NodeHasOutput, NodeHasInput {


  @Override
  public InputLink<?>[] getInputs() {
    return new InputLink[0];
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[0];
  }
}
