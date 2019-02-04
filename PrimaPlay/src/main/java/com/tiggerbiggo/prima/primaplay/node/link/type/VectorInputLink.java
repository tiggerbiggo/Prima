package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.core.render.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;
import com.tiggerbiggo.utils.calculation.Vector2;

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

