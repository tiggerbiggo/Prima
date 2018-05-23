package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.InputLink;

public interface NodeHasInput extends Node {

  InputLink<?>[] getInputs();
  InputLink<?> getInput(int n);
  default InputLink<?> getInput(){return getInput(0);}

  default boolean link(NodeHasOutput toLink, int input, int output){
    return getInput(input).link(toLink.getOutput(output));
  }
  default boolean link(NodeHasOutput toLink){
    return link(toLink, 0,0);
  }
}
