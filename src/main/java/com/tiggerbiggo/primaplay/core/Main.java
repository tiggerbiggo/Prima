package com.tiggerbiggo.primaplay.core;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.graphics.ImageTools;
import com.tiggerbiggo.primaplay.graphics.SafeImage;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.implemented.BasicRenderNode;
import com.tiggerbiggo.primaplay.node.implemented.ConstNode;
import com.tiggerbiggo.primaplay.node.implemented.MapGenNode;
import com.tiggerbiggo.primaplay.node.implemented.io.AnimationNode;
import com.tiggerbiggo.primaplay.node.implemented.io.CombineNode;
import com.tiggerbiggo.primaplay.node.implemented.io.ImageListNode;
import com.tiggerbiggo.primaplay.node.implemented.io.SuperSampleNode;
import com.tiggerbiggo.primaplay.node.implemented.io.TransformNode;
import com.tiggerbiggo.primaplay.node.implemented.io.iterative.MandelNode;

public class Main {

  public static void main(String[] args) {
    SafeImage[] imgs = ImageTools.toSafeImage(FileManager.getImgsFromFolder("imgs/cookie2/", true));

    INodeHasOutput o;
    INodeHasInput i;

    //o = new MapGenNode(new Vector2(0.015625, -1.757813),new Vector2(0.019531, -1.753906));

    o = new MapGenNode(Vector2.MINUSTWO, Vector2.TWO);
    o = new CombineNode(CombineNode.MUL, o, new ConstNode(2));

    //o = chain(o, new MandelNode(300, 0.5));

    o = chain(o, new TransformNode(TransformNode.SINSIN));
    o = new CombineNode(CombineNode.MUL, o, new ConstNode(2));
    o = chain(o, new AnimationNode());
    o = chain(o,0, new ImageListNode(imgs),1);
    o = chain(o, new SuperSampleNode(2));


    BasicRenderNode render = new BasicRenderNode();
    render.link(o);

    FileManager.writeGif(render.render(imgs[0].getWidth(), imgs[0].getHeight(), 60), "cookie3");
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
