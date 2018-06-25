package com.tiggerbiggo.primaplay.node.implemented.io;

import ch.rs.reflectorgrid.TransferGrid;
import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;
import com.tiggerbiggo.primaplay.node.core.NodeInOut;
import com.tiggerbiggo.primaplay.node.link.type.VectorInputLink;
import com.tiggerbiggo.primaplay.node.link.type.VectorOutputLink;
import java.util.function.BiFunction;

public class TransformNode extends NodeInOut {

  @TransferGrid
  private TransformFunctions function;

  private VectorInputLink input;
  private VectorOutputLink output;

  public TransformNode(TransformFunctions function) {
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

  public TransformNode() {
    this(TransformFunctions.SINSIN);
  }

  @Override
  public String getName() {
    return "Transform Node";
  }

  @Override
  public String getDescription() {
    return "Transforms a given Vector using a given Function object.";
  }
}

enum TransformFunctions {
  SINSIN((x, y) -> new Vector2(Math.sin(x), Math.sin(y))),
  SINX((x, y) -> new Vector2(Math.sin(x), y)),
  SINY((x, y) -> new Vector2(x, Math.sin(y))),

  MAGNETISM((x, y) -> {
    y = Math.sin(Math.cosh(x) * y);
    return new Vector2(x, y);
  }),
  TANNY((x, y) -> {
    y = Math.sin(Math.tanh(x) * Math.tan(y));
    return new Vector2(x, y);
  }),
  CHOPPY((x, y) -> {
    if ((Math.abs(x) + Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    if ((Math.abs(x) - Math.abs(y)) % 1 <= 0.5) {
      x *= -1;
      y *= -1;
    }
    return new Vector2(x, y);
  }),
  NEW((x, y) -> {
    if ((x - y) < 0.1) {
      return null;
    }
    return new Vector2(x, y);
  }),
  HARMONIC((x, y) -> {
    for (int i = 0; i < 100; i++) {
      x = Math.sin(x);
      y = Math.sin(y);
    }
    return new Vector2(x, y);
  }),
  NEGATE((x, y) -> {
    x *= -1;
    y *= -1;

    return new Vector2(x, y);
  }),
  TESTSUM(Vector2::new),
  SAMEX((x, y) -> {
    return new Vector2(0, y);
  }),
  SAMEY((x, y) -> {

    return new Vector2(x, 0);
  }),
  SQUAREXY((x, y) -> {
    return new Vector2(x * x, y * y);
  });

  BiFunction<Double, Double, Vector2> func;

  TransformFunctions(BiFunction<Double, Double, Vector2> func) {
    this.func = func;
  }

  public Vector2 apply(double a, double b) {
    return func.apply(a, b);
  }
}
