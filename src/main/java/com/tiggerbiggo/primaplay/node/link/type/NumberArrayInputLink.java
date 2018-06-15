package com.tiggerbiggo.primaplay.node.link.type;

import com.tiggerbiggo.primaplay.node.link.InputLink;
import com.tiggerbiggo.primaplay.node.link.OutputLink;

public class NumberArrayInputLink extends InputLink<Double[]> {
  @Override
  public boolean link(OutputLink toLink) {
    if (toLink instanceof NumberArrayOutputLink) {
      this.currentLink = (NumberArrayOutputLink) toLink;
      return true;
    }
    return false;
  }
}
