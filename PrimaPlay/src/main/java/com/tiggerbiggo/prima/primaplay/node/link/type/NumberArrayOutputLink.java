package com.tiggerbiggo.prima.primaplay.node.link.type;

import com.tiggerbiggo.prima.primaplay.core.RenderParams;
import com.tiggerbiggo.prima.primaplay.node.link.Link;
import com.tiggerbiggo.prima.primaplay.node.link.OutputLink;

public abstract class NumberArrayOutputLink extends OutputLink<Double[]> {
  public static NumberArrayOutputLink BASICLOOP = new NumberArrayOutputLink() {
    @Override
    public Double[] get(RenderParams p) {
      int n = p.frameNum();
      Double[] toReturn = new Double[n];

      for(int i=0; i<n; i++){
        toReturn[i] = (double)i/n;
      }

      return toReturn;
    }
  };

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberArrayInputLink;
  }

  @Override
  public String getStyleClass() {
    return "NumberArrayLink";
  }
}
