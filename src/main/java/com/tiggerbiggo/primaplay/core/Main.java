package com.tiggerbiggo.primaplay.core;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.graphics.SimpleGradient;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.implemented.*;
import java.awt.Color;

public class Main {

  public static void main(String[] args) {
    NodeHasOutput o;
    NodeHasInput i;

    //o = new MapGenNode(Vector2.MINUSTWO, Vector2.TWO);
    o = new MapGenNode(new Vector2(-2), new Vector2(2));
    //o = chain(o, new KaliedoNode(5));
    //o = chain(o, new MandelNode(300, 0.1));
    //o = chain(o, new TransformNode(TransformNode.SINSIN));

    //o = new CombineNode(CombineNode.MUL, o, new MapGenNode(new Vector2(0), new Vector2(20)));

    o = chain(
        chain(
            new MapGenNode(new Vector2(-3), new Vector2(3)), new TransformNode(Vector2::new)
        ), 0,
        iChain(
            chain(
                o, new MandelNode()//new TransformNode(TransformNode.SINSIN)
            ),
            new DualAnimateNode()
        ), 1
    );

    //o = chain(o, new AnimationNode());
    o = chain(o, new GradientNode(new HueCycleGradient()));
    //o = chain(o, new GradientNode(new SimpleGradient(Color.RED, Color.YELLOW, false)));
    //o = chain(o, new SuperSampleNode(3));

    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    FileManager.writeGif(render.render(200, 200, 60), "anim");
  }

  public static NodeHasOutput chain(NodeHasOutput o, int oI, NodeHasInput i, int iI){
    i.link(o, iI, oI);
    return (NodeHasOutput) i;
  }

  public static NodeHasOutput chain(NodeHasOutput o, NodeHasInput i){
    return chain(o, 0, i, 0);
  }

  public static  NodeHasInput iChain(NodeHasOutput o, int oI, NodeHasInput i, int iI){
    i.link(o, iI, oI);
    return i;
  }

  public static NodeHasInput iChain(NodeHasOutput o, NodeHasInput i){
    return iChain(o, 0, i, 0);
  }
}
