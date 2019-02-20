package com.tiggerbiggo.prima.play.node.core;

import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NodeHasOutput implements INodeHasOutput {

  List<OutputLink<?>> outputs = new ArrayList<>();

  protected void addOutput(OutputLink<?>... in) {
    outputs.addAll(Arrays.asList(in));
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return outputs.toArray(new OutputLink<?>[0]);
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    try {
      return getOutputs()[n];
    } catch (ArrayIndexOutOfBoundsException ex) {
      return null;
    }
  }
}
