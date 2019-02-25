package com.tiggerbiggo.prima.play.node.core;

import com.tiggerbiggo.prima.play.node.link.InputLink;

public interface INodeHasInput extends INode{

  InputLink<?>[] getInputs();

  InputLink<?> getInput(int n);

  default InputLink<?> getInput() {
    return getInput(0);
  }

  default boolean link(INodeHasOutput toLink, int input, int output) {
    boolean result = getInput(input).link(toLink.getOutput(output));
    if(result) onLinked();
    return result;
  }

  default void onLinked(){}

  default boolean link(INodeHasOutput toLink) {
    return link(toLink, 0, 0);
  }
}
