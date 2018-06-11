package com.tiggerbiggo.primaplay.core;

import com.tiggerbiggo.primaplay.graphics.HueCycleGradient;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.io.GradientNode;
import com.tiggerbiggo.primaplay.node.implemented.out.PixelMapNode;

public class Main {

  public static void main(String[] args) {
    INodeHasOutput o;
    INodeHasInput i;

    //o = new MapGenNode(Vector2.MINUSTWO, Vector2.TWO);
    //o = new MapGenNode(new Vector2(-8), new Vector2(8));
    //o = chain(o, new KaliedoNode(5));
    //o = chain(o, new MandelNode(300, 0.1));
    //o = chain(o, new TransformNode(TransformNode.MAGNETISM));
    //o = chain(o, new MovementNode(3));

    //o = new CombineNode(CombineNode.MUL, o, new MapGenNode(new Vector2(0), new Vector2(20)));

    /*o = chain(
        chain(
            new MapGenNode(new Vector2(-3), new Vector2(3)), new TransformNode(Vector2::new)
        ), 0,
        iChain(
            chain(
                o, new MandelNode()//new TransformNode(TransformNode.SINSIN)
            ),
            new DualAnimateNode()
        ), 1
    );*/

    o = PixelMapNode.square(400, 400);
    o = chain(o, new AnimationNode());
    o = chain(o, new GradientNode(new HueCycleGradient()));
    //o = chain(o, new GradientNode(new SimpleGradient(Color.RED, Color.YELLOW, false)));
    //o = chain(o, new SuperSampleNode(3));

    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    FileManager.writeGif(render.render(400, 400, 60), "anim");
  }

  public static INodeHasOutput chain(INodeHasOutput o, int oI, INodeHasInput i, int iI) {
    i.link(o, iI, oI);
    return (INodeHasOutput) i;
  }

  public static INodeHasOutput chain(INodeHasOutput o, INodeHasInput i) {
    return chain(o, 0, i, 0);
  }

  public static INodeHasInput iChain(INodeHasOutput o, int oI, INodeHasInput i, int iI) {
    i.link(o, iI, oI);
    return i;
  }

  public static INodeHasInput iChain(INodeHasOutput o, INodeHasInput i) {
    return iChain(o, 0, i, 0);
  }
}
