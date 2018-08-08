package com.tiggerbiggo.prima.primaplay.node.core;

import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class NodeInOut implements INodeHasInput, INodeHasOutput {

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
