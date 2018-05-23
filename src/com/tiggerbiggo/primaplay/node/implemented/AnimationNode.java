package com.tiggerbiggo.primaplay.node.implemented;

import com.tiggerbiggo.primaplay.calculation.Calculation;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeHasInput;
import com.tiggerbiggo.primaplay.node.core.NodeHasOutput;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorArrayOutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import java.util.function.Function;

public class AnimationNode implements NodeHasOutput, NodeHasInput {
  Function<Double, Vector2> func;

  public AnimationNode(Function<Double, Vector2> func) {
    this.func = func;
  }

  public AnimationNode(){
    this(SIMPLE);
  }

  VectorInputLink toAnimate = new VectorInputLink();

  final VectorArrayOutputLink animated = new VectorArrayOutputLink() {
    @Override
    public Vector2[] get(RenderParams p) {
      Vector2 start = toAnimate.get(p);
      int num = p.n();

      Vector2[] toReturn = new Vector2[num];
      for (int i = 0; i < num; i++) {
        double percent = (double) i / num;
        toReturn[i] = Vector2.add(func.apply(percent), start);
      }

      return toReturn;
    }
  };

  @Override
  public InputLink<?>[] getInputs() { return new InputLink[] {toAnimate}; }

  @Override
  public InputLink<?> getInput(int n) {
    return toAnimate;
  }

  @Override
  public OutputLink<?>[] getOutputs() {
    return new OutputLink[] {animated};
  }

  @Override
  public OutputLink<?> getOutput(int n) {
    return animated;
  }

  public static final Function<Double, Vector2> SIMPLE = Vector2::new;
  public static final Function<Double, Vector2> SINSIN = (i) -> new Vector2(
      Math.sin(i * Math.PI * 2) * (1 / Math.PI));
  public static final Function<Double, Vector2> REVERSE = (i) -> new Vector2(
      Calculation.modLoop(i, true));
  public static final Function<Double, Vector2> STILL = (i) -> new Vector2(0);
}
