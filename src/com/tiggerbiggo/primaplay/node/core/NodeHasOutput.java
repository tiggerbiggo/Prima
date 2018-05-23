package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.OutputLink;

public interface NodeHasOutput extends Node{
  OutputLink<?>[] getOutputs();

  OutputLink<?> getOutput(int n);
}
