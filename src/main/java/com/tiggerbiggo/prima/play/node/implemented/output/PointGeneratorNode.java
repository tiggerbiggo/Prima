package com.tiggerbiggo.prima.play.node.implemented.output;

import com.tiggerbiggo.prima.play.node.core.NodeHasOutput;

public class PointGeneratorNode extends NodeHasOutput {


  @Override
  public String getName() {
    return "Point Generator Node";
  }

  @Override
  public String getDescription() {
    return "Generates a set of points.";
  }
}
