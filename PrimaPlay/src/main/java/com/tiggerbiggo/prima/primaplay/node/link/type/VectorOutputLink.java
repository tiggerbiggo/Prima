package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.utils.calculation.Vector2;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;

public abstract class VectorOutputLink extends OutputLink<Vector2> {
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorInputLink;
  }
}