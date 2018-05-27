package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.calculation.Vector2;
import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class VectorArrayInputLink extends InputLink<Vector2[]> {

  @Override
  public boolean link(OutputLink toLink) {
    if (toLink instanceof VectorArrayOutputLink) {
      this.currentLink = (VectorArrayOutputLink) toLink;
      return true;
    }
    return false;
  }
}


