package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public class VectorInputLink extends InputLink<Vector2> {
  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (VectorOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorOutputLink;
  }

  @Override
  public Vector2 defaultValue(RenderParams p) {
    return Vector2.ZERO;
  }

  @Override
  public String getStyleClass() {
    return "VectorLink";
  }
}

