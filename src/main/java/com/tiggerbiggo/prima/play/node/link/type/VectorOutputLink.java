package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public abstract class VectorOutputLink extends OutputLink<Vector2> {
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorInputLink;
  }

  @Override
  public String getStyleClass() {
    return "VectorLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Vector2 got = get(p);

    if(got == null) return "null";
    return "Vector2\nX: " + got.X() + "\nY: " + got.Y();
  }
}