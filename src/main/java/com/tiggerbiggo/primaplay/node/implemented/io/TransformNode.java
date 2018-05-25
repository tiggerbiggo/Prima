package com.tiggerbiggo.primaplay.node.implemented.io;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.INodeHasInput;
import com.tiggerbiggo.primaplay.node.core.INodeHasOutput;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class TransformNode extends NodeInOut {
  private BiFunction<Double, Double, Vector2> function;
  private VectorInputLink input;
  private VectorOutputLink output;

  public TransformNode(BiFunction<Double, Double, Vector2> function) {
    this.function = function;
    input = new VectorInputLink();
    addInput(input);

    output = new VectorOutputLink() {
      @Override
      public Vector2 get(RenderParams p) {
        Vector2 tmp = input.get(p);
        return function.apply(tmp.X(), tmp.Y());
      }
    };
    addOutput(output);
  }

  public TransformNode(){
    this(SINSIN);
  }


  public static final BiFunction<Double, Double, Vector2> SINSIN = (x, y) -> new Vector2(Math.sin(x), Math.sin(y));
  public static final BiFunction<Double, Double, Vector2> SINX = (x, y) -> new Vector2(Math.sin(x), y);
  public static final BiFunction<Double, Double, Vector2> SINY = (x, y) -> new Vector2(x, Math.sin(y));
  public static final BiFunction<Double, Double, Vector2> MAGNETISM = (x, y) -> {
    y = Math.sin(Math.cosh(x) * y);
    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> TANNY = (x, y) -> {
    y = Math.sin(Math.tanh(x) * Math.tan(y));
    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> CHOPPY = (x, y) -> {
    if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    if ((Math.abs(x) - Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> NEW = (x, y) -> {
    if ((x - y) < 0.1) {
      return null;
    }
    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> HARMONIC = (x, y) -> {
    for (int i = 0; i < 100; i++) {
      x = Math.sin(x);
      y = Math.sin(y);
    }
    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> NEGATE = (x, y) -> {
    x *= -1;
    y *= -1;

    return new Vector2(x, y);
  };
  public static final BiFunction<Double, Double, Vector2> TESTSUM = Vector2::new;
  public static final BiFunction<Double, Double, Vector2> SAMEX = (x, y) -> {
    return new Vector2(0, y);
  };
  public static final BiFunction<Double, Double, Vector2> SAMEY = (x, y) -> {

    return new Vector2(x, 0);
  };
  public static final BiFunction<Double, Double, Vector2> SQUAREXY = (x, y) -> {
    return new Vector2(x * x, y * y);
  };
}
