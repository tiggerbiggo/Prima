package com.tiggerbiggo.prima.primaplay.node.implemented;

import com.tiggerbiggo.prima.primaplay.node.implemented.io.AnimationNode;

public class NodeFactory {
  public static AnimationNode stillAnim(){
    AnimationNode n = new AnimationNode(AnimationNode.STILL);
    n.link(new MapGenNode());
    return n;
  }
}
