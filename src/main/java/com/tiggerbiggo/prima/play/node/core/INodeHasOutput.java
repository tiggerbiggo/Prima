package com.tiggerbiggo.prima.play.node.core;

import com.tiggerbiggo.prima.play.node.link.OutputLink;

public interface INodeHasOutput extends INode {

  OutputLink<?>[] getOutputs();

  OutputLink<?> getOutput(int n);
}
