package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.graphics.ColorTools;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;
import java.awt.Color;

public abstract class PointOutputLink extends OutputLink<Vector2[]> {
  public PointOutputLink(String desc){
    this.desc = desc;
  }
  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof PointInputLink;
  }

  @Override
  public String getStyleClass() {
    return "PointLink";
  }

  @Override
  public String describeValue(RenderParams p, int currentFrame) {
    return "Not Implemented Yet";
  }

  @Override
  public Color[] getColors(RenderParams p) {
    return ColorTools.colorArray(p.frameNum());
  }

  @Override
  public String getReturnType() {
    return "float[]";
  }

  @Override
  public boolean isSingular() {
    return true;
  }
}
