package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.Link;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class VectorArrayInputLink extends InputLink<Vector2[]> {

  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (VectorArrayOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof VectorArrayOutputLink;
  }
}


