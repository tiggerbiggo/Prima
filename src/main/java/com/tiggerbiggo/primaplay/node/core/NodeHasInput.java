package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.InputLink;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NodeHasInput implements INodeHasInput {

  List<InputLink<?>> inputs = new ArrayList<>();

  protected void addInput(InputLink<?>... in) {
    inputs.addAll(Arrays.asList(in));
  }

  @Override
  public InputLink<?>[] getInputs() {
    return inputs.toArray(new InputLink<?>[0]);
  }

  @Override
  public InputLink<?> getInput(int n) {
    try {
      return getInputs()[n];
    } catch (ArrayIndexOutOfBoundsException ex) {
      return null;
    }
  }
}
