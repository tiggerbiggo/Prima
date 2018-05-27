package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class VectorInputLink extends InputLink<Vector2> {

  @Override
  public boolean link(OutputLink toLink) {
    if (toLink instanceof VectorOutputLink) {
      this.currentLink = (VectorOutputLink) toLink;
      return true;
    }
    return false;
  }
}

