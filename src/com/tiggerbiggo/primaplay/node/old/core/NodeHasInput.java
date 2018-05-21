package com.tiggerbiggo.primaplay.node.old.core;

import com.tiggerbiggo.primaplay.node.old.link.InputLink;

public interface NodeHasInput extends Node {

  InputLink<?>[] getInputs();
}
