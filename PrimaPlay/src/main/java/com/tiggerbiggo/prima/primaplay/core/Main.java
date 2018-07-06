package com.tiggerbiggo.prima.primaplay.core;

import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.graphics.SimpleGradient;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.prima.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.prima.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.io.GradientNode;
import com.tiggerbiggo.prima.primaplay.node.implemented.io.iterative.FourierSeriesNode;
import java.awt.Color;

public class Main {

  public static void main(String[] args) throws Throwable {
    //SafeImage[] imgs = ImageTools.toSafeImage(FileManager.getImgsFromFolder("imgs/cookie2/", true));

    INodeHasOutput o;
    INodeHasInput i;

    //o = new MapGenNode(new Vector2(0.015625, -1.757813),new Vector2(0.019531, -1.753906));

    o = new MapGenNode(Vector2.MINUSTWO, Vector2.TWO);
    //o = new CombineNode(CombineNode.MUL, o, new ConstNode(2));

    o = chain(o, new FourierSeriesNode(5));

    //o = chain(o, new MandelNode(300, 0.1));

    //o = chain(o, new TransformNode(TransformNode.SINSIN));
    //o = new CombineNode(CombineNode.MUL, o, new ConstNode(2));
    o = chain(o, new AnimationNode());
    //o = chain(o,0, new ImageListNode(imgs),1);
    //o = chain(o, new SuperSampleNode(2));

    o = chain(o, new GradientNode(new SimpleGradient(Color.RED, Color.BLUE, false)));

    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    //FileManager.writeGif(render.render(imgs[0].getWidth(), imgs[0].getHeight(), 1), "demo");
    FileManager.writeGif(render.render(100, 100, 1), "demo");
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
