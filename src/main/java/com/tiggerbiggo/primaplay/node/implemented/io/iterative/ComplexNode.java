package com.tiggerbiggo.primaplay.node.implemented.io.iterative;

import com.tiggerbiggo.primaplay.calculation.ComplexNumber;
import com.tiggerbiggo.primaplay.calculation.Vector2;

public class ComplexNode extends IterativeNode {

  ComplexFunction func;
  double multiplier;
  ComplexNumber c;

  public ComplexNode(int iter, ComplexFunction func, double multiplier, ComplexNumber c) {
    super(iter, c.asVector());

    this.c = c;

    this.func = func;
    this.multiplier = multiplier;
  }

  public ComplexNode(int iter) {
    this(iter, ZSQUARED, 0.1, new ComplexNumber(0.00003, -0.00000044));
  }

  @Override
  public Vector2 transform(Vector2 z, Vector2 c) {
    return func.apply(z.asComplex(), c.asComplex()).asVector();
  }

  @Override
  public Vector2 onEscape(Vector2 in, int currentIteration) {
    double smooth = (currentIteration + 1.0) - Math.log(Math.log(in.magnitude())) / Math.log(2);
    return new Vector2(smooth * multiplier);
  }

  @Override
  public Vector2 onBound(Vector2 in, int currentIteration) {
    double smooth = (currentIteration + 1.0) - Math.log(Math.log(in.magnitude())) / Math.log(2);
    return new Vector2(smooth * multiplier);
  }

  @Override
  public boolean escapeCheck(Vector2 in) {
    return in.sqMagnitude() > (1 << 16);
  }

  public static final ComplexFunction ZSQUARED = new ComplexFunction() {
    @Override
    public ComplexNumber apply(ComplexNumber z, ComplexNumber c) {
      return z.multiply(z).add(c);
    }
  };
  public static final ComplexFunction MANDEL = new ComplexFunction() {
    @Override
    public ComplexNumber apply(ComplexNumber z, ComplexNumber c) {
      return z.multiply(z).add(c);
    }
  };
  public static final ComplexFunction TEST = new ComplexFunction() {
    @Override
    public ComplexNumber apply(ComplexNumber z, ComplexNumber c) {
      return c.multiply(z.add(c.multiply(z.add(c))));
    }
  };

  @Override
  public String getName() {
    return "Complex Node";
  }

  @Override
  public String getDescription() {
    return "Processes complex iterative functions";
  }
}

