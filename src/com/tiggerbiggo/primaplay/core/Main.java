package com.tiggerbiggo.primaplay.core;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.graphics.SimpleGradient;
import com.tiggerbiggo.primaplay.node.core.Node;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.implemented.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.CombineNode;
import com.tiggerbiggo.primaplay.node.implemented.ConstNode;
import com.tiggerbiggo.primaplay.node.implemented.GradientNode;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.TransformNode;
import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    NodeHasOutput o;
    NodeHasInput i;

    o = new MapGenNode(Vector2.ZERO, new Vector2(20));
    o = chain(o, new TransformNode(TransformNode.SINSIN));

    o = new CombineNode(CombineNode.MUL, o, new MapGenNode(new Vector2(0), new Vector2(20)));

    o = chain(o, new AnimationNode());
    o = chain(o, new GradientNode(new SimpleGradient(Color.RED, Color.YELLOW, true)));

    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    FileManager.writeGif(render.render(new RenderParams(200, 200, 0, 0, 30)), "anim");
  }

  public static NodeHasOutput chain(NodeHasOutput o, NodeHasInput i){
    i.link(o);
    return (NodeHasOutput) i;
  }
}
