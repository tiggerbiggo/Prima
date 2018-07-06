package com.tiggerbiggo.prima.primaplay.node.core;

import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface INodeHasInput extends INode {

  List<InputLink<?>> inputs = new ArrayList<>();

  default void addInput(InputLink<?>... in) {
    inputs.addAll(Arrays.asList(in));
  }

  default InputLink<?>[] getInputs() {
    return inputs.toArray(new InputLink[]{});
  }

  default InputLink<?> getInput(int n) {
    return inputs.get(n);
  }

  default InputLink<?> getInput() {
    return getInput(0);
  }

  default boolean link(INodeHasOutput toLink, int input, int output) {
    return getInput(input).link(toLink.getOutput(output));
  }

  default boolean link(INodeHasOutput toLink) {
    return link(toLink, 0, 0);
  }
}
