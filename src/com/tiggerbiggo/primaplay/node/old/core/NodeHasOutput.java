package com.tiggerbiggo.primaplay.node.old.core;

import com.tiggerbiggo.primaplay.node.old.link.OutputLink;

public interface NodeHasOutput extends Node{

  OutputLink<?>[] getOutputs();
}
