package com.tiggerbiggo.primaplay.node.core;

import com.tiggerbiggo.primaplay.node.link.OutputLink;

public interface INodeHasOutput extends INode {

  OutputLink<?>[] getOutputs();

  OutputLink<?> getOutput(int n);
}
