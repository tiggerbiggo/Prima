package com.tiggerbiggo.primaplay.core;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.implemented.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.GradientNode;
import com.tiggerbiggo.primaplay.node.implemented.KaliedoNode;
import com.tiggerbiggo.primaplay.node.implemented.MandelNode;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;

public class Main {

  public static void main(String[] args) {
    NodeHasOutput o;
    NodeHasInput i;

    o = new MapGenNode(Vector2.MINUSTWO, Vector2.TWO);
    o = chain(o, new KaliedoNode(5));
    o = chain(o, new MandelNode(300, 0.1));
    //o = chain(o, new TransformNode(TransformNode.SINSIN));

    //o = new CombineNode(CombineNode.MUL, o, new MapGenNode(new Vector2(0), new Vector2(20)));

    o = chain(o, new AnimationNode());
    o = chain(o, new GradientNode(new HueCycleGradient()));
    //o = chain(o, new GradientNode(new SimpleGradient(Color.RED, Color.YELLOW, true)));

    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    FileManager.writeGif(render.render(200, 200, 30), "anim");
  }

  public static NodeHasOutput chain(NodeHasOutput o, NodeHasInput i){
    i.link(o);
    return (NodeHasOutput) i;
  }
}
