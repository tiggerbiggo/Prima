package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.link.InputLink;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;

public class NumberArrayInputLink extends InputLink<Double[]> {
  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (NumberArrayOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberArrayOutputLink;
  }

  @Override
  public Double[] defaultValue(RenderParams p) {
    Double[] toReturn = new Double[p.frameNum()];
    for(int i=0; i<p.frameNum(); i++){
      toReturn[i] = 0d;
    }
    return toReturn;
  }

  @Override
  public String getStyleClass() {
    return "NumberArrayLink";
  }
}
