package com.tiggerbiggo.primaplay.node.implemented.io.iterative;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.core.RenderParams;

public class FourierSeriesNode extends IterativeNode {

  public FourierSeriesNode(int iter) {
    super(iter);
  }

  @Override
  public Vector2 initZ(RenderParams p) {
    return Vector2.ZERO;
  }

  @Override
  public Vector2 initC(RenderParams p) {
    return in.get(p);
  }

  @Override
  public Vector2 transform(Vector2 z, Vector2 c, int currentIteration) {
    return z.add(Math.sin(c.magnitude() * currentIteration));
  }

  @Override
  public Vector2 onEscape(Vector2 in, int currentIteration) {
    return null;
  }

  @Override
  public Vector2 onBound(Vector2 in, int currentIteration) {
    return null;
  }

  @Override
  public boolean escapeCheck(Vector2 in, int currentIteration) {
    return false;
  }

  @Override
  public String getName() {
    return "Fourier Series Node";
  }

  @Override
  public String getDescription() {
    return "Experimental, Makes a fourier series";
  }
}
