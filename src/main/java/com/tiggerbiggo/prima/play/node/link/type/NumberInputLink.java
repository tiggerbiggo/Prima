package com.tiggerbiggo.prima.play.node.link.type;

import com.tiggerbiggo.prima.play.core.render.RenderParams;
import com.tiggerbiggo.prima.play.node.link.InputLink;
import com.tiggerbiggo.prima.play.node.link.Link;
import com.tiggerbiggo.prima.play.node.link.OutputLink;

public class NumberInputLink extends InputLink<Float> {
  public NumberInputLink(String desc){this.desc = desc;}
  @Override
  public boolean link(OutputLink toLink) {
    if (canLink(toLink)) {
      this.currentLink = (NumberOutputLink) toLink;
      return true;
    }
    return false;
  }

  @Override
  public boolean canLink(Link other) {
    if(other == null) return false;
    return other instanceof NumberOutputLink;
  }

  @Override
  public Float defaultValue(RenderParams p) {
    return (float)0.0;
  }

  @Override
  public String getStyleClass() {
    return "NumberLink";
  }
}
