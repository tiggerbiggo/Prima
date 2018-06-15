package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Calculation;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import java.util.function.Function;

public class AnimationNode extends NodeInOut {

  private Function<Double, Vector2> func;

  private VectorInputLink toAnimate;
  private VectorArrayOutputLink animated;

  public AnimationNode(Function<Double, Vector2> func) {
    this.func = func;

    toAnimate = new VectorInputLink();
    addInput(toAnimate);

    animated = new VectorArrayOutputLink() {
      @Override
      public Vector2[] get(RenderParams p) {
        Vector2 start = toAnimate.get(p);
        int num = p.frameNum();

        Vector2[] toReturn = new Vector2[num];
        for (int i = 0; i < num; i++) {
          double percent = (double) i / num;
          toReturn[i] = func.apply(percent).add(start);
        }

        return toReturn;
      }
    };
    addOutput(animated);
  }

  public AnimationNode() {
    this(SIMPLE);
  }


  public static final Function<Double, Vector2> SIMPLE = Vector2::new;
  public static final Function<Double, Vector2> SINSIN = (i) -> new Vector2(
      Math.sin(i * Math.PI * 2) * (1 / Math.PI));
  public static final Function<Double, Vector2> REVERSE = (i) -> new Vector2(
      Calculation.modLoop(i, true));
  public static final Function<Double, Vector2> STILL = (i) -> new Vector2(0);
}
