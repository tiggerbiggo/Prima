package com.tiggerbiggo.prima.play.node.implemented;

import com.tiggerbiggo.prima.play.node.implemented.io.AnimationNode;
import com.tiggerbiggo.prima.play.node.implemented.output.MapGenNode;

public class NodeFactory {
  public static AnimationNode stillAnim(){
    AnimationNode n = new AnimationNode(AnimationNode.STILL);
    n.link(new MapGenNode());
    return n;
  }
}
