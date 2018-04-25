package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.InputLink;

public interface NodeHasInput extends Node {
  public InputLink<?>[] getInputs();
}
