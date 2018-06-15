package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;

public class NodeFactory {
  public static AnimationNode stillAnim(){
    AnimationNode n = new AnimationNode(AnimationNode.STILL);
    n.link(new MapGenNode());
    return n;
  }
}
