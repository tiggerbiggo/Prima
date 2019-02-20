package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

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

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    Double[] got = get(p);

    if(got == null) return "null";
    if(currentFrame <0 || currentFrame >= got.length) return "Frame num outside bounds. Tell tiggerbiggo he's a bad coder.";

    return "Double: " + got[currentFrame];
  }
}
