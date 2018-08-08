package com.tiggerbiggo.prima.primaplay.node.core;

import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;

public interface INodeHasOutput extends INode {

  OutputLink<?>[] getOutputs();

  OutputLink<?> getOutput(int n);
}
