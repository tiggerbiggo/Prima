package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.calculation.Vector2;
import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public class PointInputLink extends InputLink<Vector2[]> {
  public PointInputLink(String desc){
    this.desc = desc;
  }

  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (PointOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof PointOutputLink;
  }

  @Override
  public Vector2[] defaultValue(RenderParams p) {
    return new Vector2[0];
  }

  @Override
  public String getStyleClass() {
    return "PointLink";
  }
}
