package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface INodeHasOutput extends INode {

  List<OutputLink<?>> outputs = new ArrayList<>();

  default void addOutput(OutputLink<?>... in) {
    outputs.addAll(Arrays.asList(in));
  }

  default OutputLink<?>[] getOutputs() {
    return outputs.toArray(new OutputLink<?>[0]);
  }

  default OutputLink<?> getOutput(int n) {
    return getOutputs()[n];
  }
}
