package com.tiggerbiggo.prima.play.node.implemented.io;

import ch.hephaistos.utilities.loki.util.annotations.TransferGrid;
import com.tiggerbiggo.prima.play.core.calculation.Calculation;
import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.core.NodeInOut;
import com.tiggerbiggo.prima.play.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.prima.play.node.link.type.VectorInputLink;
import java.util.function.Function;

public class AnimationNode extends NodeInOut {

  @TransferGrid
  private AnimFunctions func = AnimFunctions.SIMPLE;

  private VectorInputLink toAnimate;
  private VectorArrayOutputLink animated;

  public AnimationNode() {
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

  @Override
  public String getName() {
    return "Animation Node";
  }

  @Override
  public String getDescription() {
    return "Animates given input vector to produce animated array of vectors.";
  }
}

enum AnimFunctions{
  SIMPLE(Vector2::new),
  SINSIN ((i) -> new Vector2(Math.sin(i * Math.PI * 2) / Math.PI)),
  SINCOS ((i) -> new Vector2(Math.sin(i * Math.PI * 2) / Math.PI, Math.cos(i * Math.PI * 2) / Math.PI)),
  REVERSE ((i) -> new Vector2(Calculation.modLoop(i, true))),
  STILL ((i) -> new Vector2(0)),
  JUSTX ((i) -> new Vector2(i, 0)),
  JUSTY ((i) -> new Vector2(0, i));

  Function<Double, Vector2> func;
  AnimFunctions(Function<Double, Vector2> _func){
    func = _func;
  }

  public Vector2 apply(double in){
    return func.apply(in);
  }
}
